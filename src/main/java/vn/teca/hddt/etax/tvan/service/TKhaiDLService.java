package vn.teca.hddt.etax.tvan.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.ObjectPool;
import org.javatuples.Triplet;
import org.springframework.stereotype.Service;
import vn.teca.hddt.etax.tvan.exception.FatalException;
import vn.teca.hddt.etax.tvan.exception.JAXBValidationException;
import vn.teca.hddt.etax.tvan.mapper.TKhaiDLMapper;
import vn.teca.hddt.etax.tvan.model.EtaxTDiep;
import vn.teca.hddt.etax.tvan.model.SenderInfo;
import vn.teca.hddt.etax.tvan.model.TKhaiDL;
import vn.teca.hddt.etax.tvan.model.enums.ErrorCode;
import vn.teca.hddt.etax.tvan.model.enums.EtaxTTXLy;
import vn.teca.hddt.etax.tvan.pool.MarshallerPool;
import vn.teca.hddt.etax.tvan.pool.UnmarshallerPool;
import vn.teca.hddt.etax.tvan.service.grpc.KeyService;
import vn.teca.hddt.etax.tvan.service.grpc.TINStatusService;
import vn.teca.hddt.etax.tvan.util.RegexCommon;
import vn.teca.hddt.etax.tvan.xml.ResponseXml;
import vn.teca.hddt.etax.tvan.xml.TKhaiDLXml;

import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TKhaiDLService {

    private static final String MNNHAN = "HDDT";
    private final ObjectPool<Unmarshaller> unmarshallerTKhaiDLXml;
    private final ObjectPool<Marshaller> marshallerTKhaiDLXml;
    private final ResponseMessageBuilder responseMessageBuilder;
    private final XmlSplitter xmlSplitter;
    private final KeyService keyService;
    private final DataService dataService;
    private final TINStatusService tinStatusService;

    public TKhaiDLXml toTKhaiDLObject(String xml) throws JAXBValidationException {
        try {
            return new UnmarshallerPool(unmarshallerTKhaiDLXml).xmlToObject(xml, TKhaiDLXml.class);
        } catch (JAXBValidationException ex) {
            log.error("Không thể parse xml tờ khai 03: {}", xml, ex);
            throw new JAXBValidationException(ex.getMessage(), ErrorCode.ERR_80000);
        }
    }

    public String toTKhaiDLXml(TKhaiDLXml entity) throws JAXBValidationException {
        try {
            return new MarshallerPool(marshallerTKhaiDLXml).objectToXml(entity, TKhaiDLXml.class);
        } catch (JAXBValidationException ex) {
            log.error("Không thể convert xml tờ khai 03: {}", entity, ex);
            throw new JAXBValidationException(ex.getMessage(), ErrorCode.ERR_80000);
        }
    }

    private void checkExists(TKhaiDL item) throws FatalException {
        boolean isValid;
        try {
            isValid = keyService.validate(String.format("%s_%s_%s", item.getLktthue(), item.getKtthue(), item.getStkhai()));
        } catch (Exception ex) {
            throw new FatalException(ErrorCode.ERROR_CACHE);
        }
        if (!isValid)
            throw new FatalException(ErrorCode.ERR_80001);
    }

    public void process(String payload){
        // lưu thông điệp gốc vào bảng etax_tdiep
        UUID hsg = dataService.saveHSGTDiep(payload);

        // bóc thông tin header
        SenderInfo senderInfo;
        try {
            senderInfo = responseMessageBuilder.extract(payload);
        } catch (FatalException ex) {
            // trường hợp không bóc được thông tin header => lưu vào bảng etax_tdkhle/etax_tdloi, tạo phản hồi và dừng luồng
            saveInvalidMessage(hsg, payload, ex);
            responseMessageBuilder.buildErrorResponse(ex);
            return;
        }

        // kiểm tra thông tin header đã đúng chuẩn
        String msgHeaderValid = responseMessageBuilder.validateHeaderResponse(senderInfo);
        if (!msgHeaderValid.isEmpty()) {
            // trường hợp chưa đúng chuẩn, đã tạo phản hồi khi validate nên chỉ cần lưu lại nội dung lỗi
            saveInvalidMessage(hsg, payload, new FatalException(msgHeaderValid));
            return;
        }

        try {
            // chia nhỏ gói tin và parse gói dữ liệu
            List<String> packages;
            try {
                packages = xmlSplitter.splitByElement(payload, "TKhai");
            } catch (Exception ex) {
                throw new FatalException(ex.getMessage(), ErrorCode.ERR_80000);
            }

            // todo: Xuống đến đây là thông điệp gốc đã chuẩn, lưu vào etax_tdiep
            EtaxTDiep etaxTDiep = EtaxTDiep.builder()
                    .msgid(senderInfo.getMsgId())
                    .mngui(senderInfo.getSenderCode())
                    .mnnhan(MNNHAN)
                    .hsgoc(hsg)
                    .ttxly(EtaxTTXLy.RECEIVED)
                    .ntao(Instant.now())
                    .ncnhat(Instant.now())
                    .build();
            dataService.saveEtaxTDiep(etaxTDiep);

            List<Triplet<Integer, String, ErrorCode>> errorCodes = new ArrayList<>();
            int counter = 0;
            for (String pack : packages) {
                counter++;
                try {
                    // parse tờ khai
                    TKhaiDLXml tkhaiDLXml = toTKhaiDLObject(pack);
                    tkhaiDLXml.setMsgid(senderInfo.getMsgId());
                    // set mcqtqly
                    try {
                        tinStatusService.findByTIN(tkhaiDLXml.getMst())
                                .ifPresent(tinStatus -> tkhaiDLXml.setMcqtqly(tinStatus.getCqtqly()));
                    } catch (Exception ignored) {
                    }

                    TKhaiDL tkhaiDL = TKhaiDLMapper.fromXml(tkhaiDLXml);

                    // kiểm tra trùng key
                    checkExists(tkhaiDL);

                    // lưu hồ sơ gốc
                    UUID fileId = dataService.saveHSGTKDLieu(pack);
                    tkhaiDL.setHsgoc(fileId);

                    // lưu dữ liệu vào bảng chung và chi tiết
                    dataService.saveTKDLieu(tkhaiDL);
                } catch (JAXBValidationException ex) {
                    errorCodes.add(Triplet.with(counter, ex.getMessage(), ex.getErrorCode()));
                } catch (FatalException ex) {
                    errorCodes.add(Triplet.with(counter, ex.getMessage(), ex.getErrorCode()));
                }
            }
            //Luu thông điệp trả ra
            String msgid = MNNHAN + UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
            EtaxTDiep etaxTDiepResponse = EtaxTDiep.builder()
                    .msgid(msgid)
                    .mngui(MNNHAN)
                    .mnnhan(senderInfo.getSenderCode())
                    .msgrefid(senderInfo.getMsgId())
                    .ncnhat(Instant.now())
                    .ntao(Instant.now())
                    .build();
            // build thông điệp phản hồi các trường hợp handle
            if (errorCodes.size() == 0) {
                // trường hợp không lỗi
                String responseXml = responseMessageBuilder.buildSuccessResponse(senderInfo);
                UUID hsgTDiepResponse = dataService.saveHSGTDiep(responseXml);
                etaxTDiepResponse.setHsgoc(hsgTDiepResponse);
                etaxTDiepResponse.setTtxly(EtaxTTXLy.VALID);

                // todo: cập nhật trạng thái xử lý về thành công
                etaxTDiep.setNcnhat(Instant.now());
                etaxTDiep.setTtxly(EtaxTTXLy.VALID);
                dataService.saveEtaxTDiep(etaxTDiep);
            } else {
                String responseXml = responseMessageBuilder.buildErrorResponse(senderInfo, errorCodes);
                UUID hsgTDiepResponse = dataService.saveHSGTDiep(responseXml);
                etaxTDiepResponse.setHsgoc(hsgTDiepResponse);
                etaxTDiepResponse.setTtxly(EtaxTTXLy.INVALID);

                // todo: cập nhật trạng thái xử lý về lỗi
                etaxTDiep.setNcnhat(Instant.now());
                etaxTDiep.setTtxly(EtaxTTXLy.INVALID);
                dataService.saveEtaxTDiep(etaxTDiep);
            }
            dataService.saveEtaxTDiep(etaxTDiepResponse);
        } catch (FatalException ex) {
            log.error(ex.getMessage(), ex);
            saveInvalidMessage(hsg, payload, ex);
            // todo: tuning tạo thông điệp phản hồi
            responseMessageBuilder.buildErrorResponse(senderInfo, ex);
        } catch (Exception ex) {
            log.error("Lỗi không xác định: " + ex.getMessage(), ex);
            saveInvalidMessage(hsg, payload, new FatalException(ex.getMessage()));
            responseMessageBuilder.buildErrorResponse(senderInfo, ex);
        }
    }

    private void saveInvalidMessage(UUID hsg, String payload, FatalException ex) {
        // cố gắng dùng regex bắt msgid
        String msgId = RegexCommon.extractMsgId(payload);
        if (msgId.isEmpty()) {
            // nếu không tìm được msgid => lưu vào bảng hddt_etax_tdkhle
            dataService.saveTDKHLe(hsg, ErrorCode.ERR_80000.getKey(), ex.getMessage());
        } else {
            // lưu vào bảng hddt_etax_tdloi
            dataService.saveTDLoi(msgId, hsg, ErrorCode.ERR_80000.getKey(), ex.getMessage());
        }
    }
}
