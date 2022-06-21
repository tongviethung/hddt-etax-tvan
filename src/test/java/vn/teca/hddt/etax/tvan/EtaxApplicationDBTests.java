package vn.teca.hddt.etax.tvan;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import vn.teca.hddt.etax.tvan.exception.JAXBValidationException;
import vn.teca.hddt.etax.tvan.mapper.TKhaiDLMapper;
import vn.teca.hddt.etax.tvan.model.TKhaiDL;
import vn.teca.hddt.etax.tvan.repository.InventoryMapper;
import vn.teca.hddt.etax.tvan.service.EsbConsumer;
import vn.teca.hddt.etax.tvan.service.TKhaiDLService;
import vn.teca.hddt.etax.tvan.xml.TKhaiDLXml;
import vn.teca.hddt.etax.tvan.xml.enums.LKTThueXml;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

@Slf4j
@SpringBootTest
class EtaxApplicationDBTests {

    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private TKhaiDLService tkhaiDLService;

    @Autowired
    private EsbConsumer esbConsumer;

    @Test
    void testEsbResponse(){
        String payload = "<DATA>\n" +
                "\t<HEADER>\n" +
                "\t\t<VERSION>1.0</VERSION>\n" +
                "\t\t<SENDER_CODE>ETAX</SENDER_CODE>\n" +
                "\t\t<SENDER_NAME>Hệ thống ETAX</SENDER_NAME>\n" +
                "\t\t<RECEIVER_CODE>HDDT</RECEIVER_CODE>\n" +
                "\t\t<RECEIVER_NAME>Hệ thống HDDT</RECEIVER_NAME>\n" +
                "\t\t<TRAN_CODE>09005</TRAN_CODE>\n" +
                "\t\t<MSG_ID>ETAX0000000000000001</MSG_ID>\n" +
                "\t\t<MSG_REFID/>\n" +
                "\t\t<SEND_DATE>2022-06-15T10:58:29\n" +
                "\t</HEADER>\n" +
                "\t<BODY>\n" +
                "\t\t<TKhai>\n" +
                "\t\t\t<DLTKhai>\n" +
                "\t\t\t\t<TTChung>\n" +
                "\t\t\t\t\t<PBan>2.0.0</PBan>\n" +
                "\t\t\t\t\t<MSo>03/DL-HĐĐT</MSo>\n" +
                "\t\t\t\t\t<Ten>TK dữ liệu hóa đơn, chứng từ hàng hóa, dịch vụ bán ra theo quy định tại Nghị định 123/2020/NĐ-CP</Ten>\n" +
                "\t\t\t\t\t<LKTThue>N</LKTThue>\n" +
                "\t\t\t\t\t<KTThue>01/06/2022</KTThue>\n" +
                "\t\t\t\t\t<STKhai>1</STKhai>\n" +
                "\t\t\t\t\t<DDanh>Hà Nội</DDanh>\n" +
                "\t\t\t\t\t<NLap>2022-06-15T10:58:29</NLap>\n" +
                "\t\t\t\t\t<TNNT>Nguyễn Văn A</TNNT>\n" +
                "\t\t\t\t\t<MST>0123456789</MST>\n" +
                "\t\t\t\t\t<TDLThue>Đại lý thuế Cầu Giấy</TDLThue>\n" +
                "\t\t\t\t\t<MSTDLThue>9876543210</MSTDLThue>\n" +
                "\t\t\t\t\t<DVTTe>VNĐ</DVTTe>\n" +
                "\t\t\t\t</TTChung>\n" +
                "\t\t\t\t<NDTKhai>\n" +
                "\t\t\t\t\t<TTSuat>\n" +
                "\t\t\t\t\t\t<TSuat>5%</TSuat>\n" +
                "\t\t\t\t\t\t<DSHDon>\n" +
                "\t\t\t\t\t\t\t<HDon>\n" +
                "\t\t\t\t\t\t\t\t<STT>1</STT>\n" +
                "\t\t\t\t\t\t\t\t<KHMSHDon>1</KHMSHDon>\n" +
                "\t\t\t\t\t\t\t\t<KHHDon>C22TAA</KHHDon>\n" +
                "\t\t\t\t\t\t\t\t<SHDon>1</SHDon>\n" +
                "\t\t\t\t\t\t\t\t<NLap>2022-06-15T10:58:29</NLap>\n" +
                "\t\t\t\t\t\t\t\t<TNMua>Người Mua Duy Nhất</TNMua>\n" +
                "\t\t\t\t\t\t\t\t<MSTNMua>0000000000</MSTNMua>\n" +
                "\t\t\t\t\t\t\t\t<DTCThue>10000</DTCThue>\n" +
                "\t\t\t\t\t\t\t\t<TGTGT>500</TGTGT>\n" +
                "\t\t\t\t\t\t\t\t<GChu>Hóa đơn 3</GChu>\n" +
                "\t\t\t\t\t\t\t</HDon>\n" +
                "\t\t\t\t\t\t\t<HDon>\n" +
                "\t\t\t\t\t\t\t\t<STT>2</STT>\n" +
                "\t\t\t\t\t\t\t\t<KHMSHDon>1</KHMSHDon>\n" +
                "\t\t\t\t\t\t\t\t<KHHDon>C22TAA</KHHDon>\n" +
                "\t\t\t\t\t\t\t\t<SHDon>2</SHDon>\n" +
                "\t\t\t\t\t\t\t\t<NLap>2022-06-15T10:58:29</NLap>\n" +
                "\t\t\t\t\t\t\t\t<TNMua>Người Mua Duy Nhất Còn Lại</TNMua>\n" +
                "\t\t\t\t\t\t\t\t<MSTNMua>1111111111</MSTNMua>\n" +
                "\t\t\t\t\t\t\t\t<DTCThue>20000</DTCThue>\n" +
                "\t\t\t\t\t\t\t\t<TGTGT>1000</TGTGT>\n" +
                "\t\t\t\t\t\t\t\t<GChu>Hóa đơn 4</GChu>\n" +
                "\t\t\t\t\t\t\t</HDon>\n" +
                "\t\t\t\t\t\t</DSHDon>\n" +
                "\t\t\t\t\t\t<TgDTCThue>30000</TgDTCThue>\n" +
                "\t\t\t\t\t\t<TgTGTGT>1500</TgTGTGT>\n" +
                "\t\t\t\t\t</TTSuat>\n" +
                "\t\t\t\t\t<TTSuat>\n" +
                "\t\t\t\t\t\t<TSuat>10%</TSuat>\n" +
                "\t\t\t\t\t\t<DSHDon>\n" +
                "\t\t\t\t\t\t\t<HDon>\n" +
                "\t\t\t\t\t\t\t\t<STT>1</STT>\n" +
                "\t\t\t\t\t\t\t\t<KHMSHDon>1</KHMSHDon>\n" +
                "\t\t\t\t\t\t\t\t<KHHDon>C22TAA</KHHDon>\n" +
                "\t\t\t\t\t\t\t\t<SHDon>3</SHDon>\n" +
                "\t\t\t\t\t\t\t\t<NLap>2022-06-15T10:58:29</NLap>\n" +
                "\t\t\t\t\t\t\t\t<TNMua>Người Mua Duy Nhất</TNMua>\n" +
                "\t\t\t\t\t\t\t\t<MSTNMua>0000000000</MSTNMua>\n" +
                "\t\t\t\t\t\t\t\t<DTCThue>8000</DTCThue>\n" +
                "\t\t\t\t\t\t\t\t<TGTGT>800</TGTGT>\n" +
                "\t\t\t\t\t\t\t\t<GChu>Hóa đơn 1</GChu>\n" +
                "\t\t\t\t\t\t\t</HDon>\n" +
                "\t\t\t\t\t\t\t<HDon>\n" +
                "\t\t\t\t\t\t\t\t<STT>2</STT>\n" +
                "\t\t\t\t\t\t\t\t<KHMSHDon>1</KHMSHDon>\n" +
                "\t\t\t\t\t\t\t\t<KHHDon>C22TAA</KHHDon>\n" +
                "\t\t\t\t\t\t\t\t<SHDon>4</SHDon>\n" +
                "\t\t\t\t\t\t\t\t<NLap>2022-06-15T10:58:29</NLap>\n" +
                "\t\t\t\t\t\t\t\t<TNMua>Người Mua Duy Nhất Còn Lại</TNMua>\n" +
                "\t\t\t\t\t\t\t\t<MSTNMua>1111111111</MSTNMua>\n" +
                "\t\t\t\t\t\t\t\t<DTCThue>2000</DTCThue>\n" +
                "\t\t\t\t\t\t\t\t<TGTGT>200</TGTGT>\n" +
                "\t\t\t\t\t\t\t\t<GChu>Hóa đơn 2</GChu>\n" +
                "\t\t\t\t\t\t\t</HDon>\n" +
                "\t\t\t\t\t\t</DSHDon>\n" +
                "\t\t\t\t\t\t<TgDTCThue>10000</TgDTCThue>\n" +
                "\t\t\t\t\t\t<TgTGTGT>1000</TgTGTGT>\n" +
                "\t\t\t\t\t</TTSuat>\n" +
                "\t\t\t\t\t<TgDThu>40000</TgDThu>\n" +
                "\t\t\t\t\t<TgThue>2500</TgThue>\n" +
                "\t\t\t\t</NDTKhai>\n" +
                "\t\t\t</DLTKhai>\n" +
                "\t\t</TKhai>\n" +
                "\t</BODY>\n" +
                "</DATA>";
        Message<String> message = new GenericMessage<>(payload);
        esbConsumer.listener(message);
    }

    @Test
    void testImportTKhaiXml() throws Exception {
        String xml = "<TKhai><DLTKhai><TTChung><PBan>2.0.0</PBan><MSo>03/DL-HĐĐT</MSo><Ten>TK dữ liệu hóa đơn, chứng từ hàng hóa, dịch vụ bán ra theo quy định tại Nghị định 123/2020/NĐ-CP</Ten><LKTThue>N</LKTThue><KTThue>01/06/2022</KTThue><STKhai>1</STKhai><DDanh>Hà Nội</DDanh><NLap>2022-06-15T10:58:29</NLap><TNNT>Nguyễn Văn A</TNNT><MST>0123456789</MST><TDLThue>Đại lý thuế Cầu Giấy</TDLThue><MSTDLThue>9876543210</MSTDLThue><DVTTe>VNĐ</DVTTe></TTChung><NDTKhai><TTSuat><TSuat>10%</TSuat><DSHDon><HDon><STT>1</STT><KHMSHDon>1</KHMSHDon><KHHDon>C22TAA</KHHDon><SHDon>1</SHDon><NLap>2022-06-15T10:58:29</NLap><TNMua>Người Mua Duy Nhất</TNMua><MSTNMua>0000000000</MSTNMua><DTCThue>8000</DTCThue><TGTGT>800</TGTGT><GChu>Hóa đơn 1</GChu></HDon><HDon><STT>2</STT><KHMSHDon>1</KHMSHDon><KHHDon>C22TAA</KHHDon><SHDon>2</SHDon><NLap>2022-06-15T10:58:29</NLap><TNMua>Người Mua Duy Nhất Còn Lại</TNMua><MSTNMua>1111111111</MSTNMua><DTCThue>2000</DTCThue><TGTGT>200</TGTGT><GChu>Hóa đơn 2</GChu></HDon></DSHDon><TgDTCThue>10000</TgDTCThue><TgTGTGT>1000</TgTGTGT></TTSuat><TTSuat><TSuat>5%</TSuat><DSHDon><HDon><STT>1</STT><KHMSHDon>1</KHMSHDon><KHHDon>C22TAA</KHHDon><SHDon>3</SHDon><NLap>2022-06-15T10:58:29</NLap><TNMua>Người Mua Duy Nhất</TNMua><MSTNMua>0000000000</MSTNMua><DTCThue>10000</DTCThue><TGTGT>500</TGTGT><GChu>Hóa đơn 3</GChu></HDon><HDon><STT>2</STT><KHMSHDon>1</KHMSHDon><KHHDon>C22TAA</KHHDon><SHDon>4</SHDon><NLap>2022-06-15T10:58:29</NLap><TNMua>Người Mua Duy Nhất Còn Lại</TNMua><MSTNMua>1111111111</MSTNMua><DTCThue>20000</DTCThue><TGTGT>1000</TGTGT><GChu>Hóa đơn 4</GChu></HDon></DSHDon><TgDTCThue>30000</TgDTCThue><TgTGTGT>1500</TgTGTGT></TTSuat><TgDThu>40000</TgDThu><TgThue>2500</TgThue></NDTKhai></DLTKhai></TKhai>\n";

        TKhaiDLXml tkhaiDLXml = tkhaiDLService.toTKhaiDLObject(xml);
        TKhaiDL tkhaiDL = TKhaiDLMapper.fromXml(tkhaiDLXml);
        inventoryMapper.tkhaiDLRepository().save(tkhaiDL);
        inventoryMapper.tkhaiCTietRepository().saveList(tkhaiDL.getTkdlctiets());
    }

    @Test
    void testExportTKhaiXml() throws JAXBValidationException {
        TKhaiDLXml tkhaiDLXml = new TKhaiDLXml();
        tkhaiDLXml.setPban("2.0.0");
        tkhaiDLXml.setMso("03/DL-HĐĐT");
        tkhaiDLXml.setTen("TK dữ liệu hóa đơn, chứng từ hàng hóa, dịch vụ bán ra theo quy định tại Nghị định 123/2020/NĐ-CP");
        tkhaiDLXml.setLktthue(LKTThueXml.N);
        tkhaiDLXml.setKtthue("01/06/2022");
        tkhaiDLXml.setStkhai(1);
        tkhaiDLXml.setDdanh("Hà Nội");
        tkhaiDLXml.setNlap(new Date());
        tkhaiDLXml.setTnnt("Nguyễn Văn A");
        tkhaiDLXml.setMst("0123456789");
        tkhaiDLXml.setTdlthue("Đại lý thuế Cầu Giấy");
        tkhaiDLXml.setMstdlthue("9876543210");
        tkhaiDLXml.setDvtte("VNĐ");

        TKhaiDLXml.TTSuatXml ttsuat = new TKhaiDLXml.TTSuatXml();

        TKhaiDLXml.HDonXml hdon = new TKhaiDLXml.HDonXml();
        hdon.setStt(1);
        hdon.setKhmshdon("1");
        hdon.setKhhdon("C22TAA");
        hdon.setShdon("1");
        hdon.setNlap(new Date());
        hdon.setTnmua("Người Mua Duy Nhất");
        hdon.setMstnmua("0000000000");
        hdon.setDtcthue(BigDecimal.valueOf(8000));
        hdon.setTgtgt(BigDecimal.valueOf(800));
        hdon.setGchu("Hóa đơn 1");

        TKhaiDLXml.HDonXml hdon1 = new TKhaiDLXml.HDonXml();
        hdon1.setStt(2);
        hdon1.setKhmshdon("1");
        hdon1.setKhhdon("C22TAA");
        hdon1.setShdon("2");
        hdon1.setNlap(new Date());
        hdon1.setTnmua("Người Mua Duy Nhất Còn Lại");
        hdon1.setMstnmua("1111111111");
        hdon1.setDtcthue(BigDecimal.valueOf(2000));
        hdon1.setTgtgt(BigDecimal.valueOf(200));
        hdon1.setGchu("Hóa đơn 2");
        ttsuat.setHdon(Arrays.asList(hdon, hdon1));

        ttsuat.setTgdtcthue(BigDecimal.valueOf(10000));
        ttsuat.setTgtgtgt(BigDecimal.valueOf(1000));
        ttsuat.setTsuat("10%");

        tkhaiDLXml.setTtsuat(Collections.singletonList(ttsuat));
        tkhaiDLXml.setTgdthu(BigDecimal.valueOf(10000));
        tkhaiDLXml.setTgthue(BigDecimal.valueOf(1000));

        String xml = tkhaiDLService.toTKhaiDLXml(tkhaiDLXml);
        log.info(xml);
    }





}
