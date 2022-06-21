package vn.teca.hddt.etax.tvan.service;

import java.io.StringReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.ObjectPool;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import vn.teca.hddt.etax.tvan.exception.FatalException;
import vn.teca.hddt.etax.tvan.exception.JAXBValidationException;
import vn.teca.hddt.etax.tvan.model.DocTaxDto;
import vn.teca.hddt.etax.tvan.model.SenderInfo;
import vn.teca.hddt.etax.tvan.model.enums.ErrorCode;
import vn.teca.hddt.etax.tvan.pool.MarshallerPool;
import vn.teca.hddt.etax.tvan.xml.ResponseXml;

@Service
@Slf4j
public class ResponseMessageBuilder {

    private static final String DATASERVICE_NAME = "ETAX";
    private final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
    @Autowired
    private ObjectPool<Marshaller> marshallerResponseXml;

    @Autowired
    private EsbProducer esbProducer;

    public SenderInfo extract(String payload) throws FatalException {
        SenderInfo senderInfo = new SenderInfo();
        try {
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(new StringReader(payload));
            while (reader.hasNext()) {
                XMLEvent nextEvent = reader.nextEvent();
                if (nextEvent.isStartElement()) {
                    StartElement startElement = nextEvent.asStartElement();
                    switch (startElement.getName().getLocalPart()) {
                        case "SENDER_CODE":
                            nextEvent = reader.nextEvent();
                            senderInfo.setSenderCode(nextEvent.asCharacters().getData());
                            break;
                        case "SENDER_NAME":
                            nextEvent = reader.nextEvent();
                            senderInfo.setSenderName(nextEvent.asCharacters().getData());
                            break;
                        case "TRAN_CODE":
                            nextEvent = reader.nextEvent();
                            senderInfo.setTransCode(nextEvent.asCharacters().getData());
                            break;
                        case "MSG_ID":
                            nextEvent = reader.nextEvent();
                            senderInfo.setMsgId(nextEvent.asCharacters().getData());
                            break;
                    }
                }
                if (nextEvent.isEndElement()) {
                    EndElement endElement = nextEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("HEADER")) {
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new FatalException("Lỗi bóc thông tin header: " + ex.getMessage(), ErrorCode.ERR_80003);
        }
        return senderInfo;
    }

    public ResponseXml buildResponseTemplate(SenderInfo senderInfo) {
        ResponseXml responseXml = ResponseXml.builder()
                .version("1.0")
                .senderCode("HDDT")
                .senderName("Hóa đơn điện tử")
                .msgId(999)
                .sendDate(Date.from(Instant.now()))
                .build();

        if (senderInfo != null) {
            responseXml.setReceiverCode(senderInfo.getSenderCode());
            responseXml.setReceiverName(senderInfo.getSenderName());
            responseXml.setTranCode(senderInfo.getTransCode());
            responseXml.setMsgRefid(senderInfo.getMsgId());
        }
        return responseXml;
    }

    public void buildErrorResponse(FatalException e){
        ResponseXml responseXml = buildResponseTemplate(null);
        responseXml.setMsgStatus("00");
        responseXml.setMsgStatusDesc(e.getMessage());
        try {
            esbProducer.send(new MarshallerPool(marshallerResponseXml).objectToXml(responseXml, ResponseXml.class));
        } catch (Exception ex) {
            log.error("Cannot build response message", ex);
        }
    }

    public String buildSuccessResponse(SenderInfo senderInfo) throws JAXBValidationException {
        ResponseXml responseXml = buildResponseTemplate(senderInfo);
        responseXml.setMsgStatus("01");
        String strXml = new MarshallerPool(marshallerResponseXml).objectToXml(responseXml, ResponseXml.class);
        try {
            esbProducer.send(strXml);
        } catch (Exception ex) {
            log.error("Cannot build response message", ex);
        }
        return strXml;
    }

    public String validateHeaderResponse(SenderInfo senderInfo) {
        List<String> errorMsgs = new ArrayList<>();
        // valid data
        if (StringUtils.isBlank(senderInfo.getSenderCode())) {
            errorMsgs.add("Nội dung thẻ SENDER_CODE không được bỏ trống");
        }
        if (StringUtils.isBlank(senderInfo.getSenderName())) {
            errorMsgs.add("Nội dung thẻ SENDER_NAME không được bỏ trống");
        }
        if (StringUtils.isBlank(senderInfo.getTransCode())) {
            errorMsgs.add("Nội dung thẻ TRAN_CODE không được bỏ trống");
        }
        if (StringUtils.isBlank(senderInfo.getMsgId())) {
            errorMsgs.add("Nội dung thẻ MSG_ID không được bỏ trống");
        }
        if (StringUtils.isNotBlank(senderInfo.getSenderCode()) && !senderInfo.getSenderCode().equalsIgnoreCase(DATASERVICE_NAME)) {
            errorMsgs.add(String.format("Giá trị thẻ SENDER_CODE không hợp lệ, chỉ cho phép giá trị: %s", DATASERVICE_NAME));
        }

        if (errorMsgs.size() > 0) {
            ResponseXml responseXml = buildResponseTemplate(senderInfo);
            responseXml.setMsgStatus("00");
            responseXml.setMsgStatusDesc(String.join("; ", errorMsgs));
            try {
                esbProducer.send(new MarshallerPool(marshallerResponseXml).objectToXml(responseXml, ResponseXml.class));
            } catch (Exception ex) {
                log.error("Cannot build response message", ex);
            }
            return responseXml.getMsgStatusDesc();
        }
        return "";
    }

    public String buildErrorResponse(SenderInfo senderInfo, List<Triplet<Integer, String, ErrorCode>> errorCodes) throws JAXBValidationException {
        ResponseXml responseXml = buildResponseTemplate(senderInfo);
        responseXml.setMsgStatus("00");
        responseXml.setMsgStatusDesc(errorCodes.stream().map(e -> String.format("%s | %s | %s", e.getValue0(), e.getValue1(), e.getValue2().getKey())).collect(Collectors.joining("; ")));
        String strXml = new MarshallerPool(marshallerResponseXml).objectToXml(responseXml, ResponseXml.class);
        try {
            esbProducer.send(strXml);
        } catch (Exception ex) {
            log.error("Cannot build response message", ex);
        }
        return strXml;
    }

    public void buildErrorResponse(SenderInfo senderInfo, Exception e) {
        ResponseXml responseXml = buildResponseTemplate(senderInfo);
        responseXml.setMsgStatus("00");
        responseXml.setMsgStatusDesc(e.getMessage());
        try {
            esbProducer.send(new MarshallerPool(marshallerResponseXml).objectToXml(responseXml, ResponseXml.class));
        } catch (Exception ex) {
            log.error("Cannot build response message", ex);
        }

    }

	public DocTaxDto extract2(String payload) throws FatalException{
		DocTaxDto senderInfo = new DocTaxDto();
        try {
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(new StringReader(payload));
            while (reader.hasNext()) {
                XMLEvent nextEvent = reader.nextEvent();
                if (nextEvent.isStartElement()) {
                    StartElement startElement = nextEvent.asStartElement();
                    switch (startElement.getName().getLocalPart()) {
                        case "soGiayPhepSX":
                            nextEvent = reader.nextEvent();
                            senderInfo.setSoGiayPhepSX(nextEvent.asCharacters().getData());
                            break;
                        case "noiCapGiayPhep":
                            nextEvent = reader.nextEvent();
                            senderInfo.setNoiCapGiayPhep(nextEvent.asCharacters().getData());
                            break;
                        case "ngayGiayPhepSX":
                            nextEvent = reader.nextEvent();
                            senderInfo.setNgayGiayPhepSX(nextEvent.asCharacters().getData());
                            break;
                        case "sanLuong":
                            nextEvent = reader.nextEvent();
                            senderInfo.setSanLuong(nextEvent.asCharacters().getData());
                            break;
                        case "thoiHan":
                            nextEvent = reader.nextEvent();
                            senderInfo.setThoiHan(nextEvent.asCharacters().getData());
                            break;
                    }
                }
                if (nextEvent.isEndElement()) {
                    EndElement endElement = nextEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("HEADER")) {
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new FatalException("Lỗi bóc thông tin header: " + ex.getMessage(), ErrorCode.ERR_80003);
        }
        return senderInfo; 
	}
}
