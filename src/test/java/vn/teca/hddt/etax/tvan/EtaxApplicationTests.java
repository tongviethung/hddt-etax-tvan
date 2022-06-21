package vn.teca.hddt.etax.tvan;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vn.teca.hddt.etax.tvan.exception.JAXBValidationException;
import vn.teca.hddt.etax.tvan.pool.MarshallerPoolFactory;
import vn.teca.hddt.etax.tvan.pool.UnmarshallerPoolFactory;
import vn.teca.hddt.etax.tvan.service.TKhaiDLService;
import vn.teca.hddt.etax.tvan.xml.TKhaiDLXml;
import vn.teca.hddt.etax.tvan.xml.enums.LKTThueXml;

import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
class EtaxApplicationTests {

    private TKhaiDLService tkhaiDLService;

    @BeforeEach
    void setupService(){
//        ObjectPool<Unmarshaller> unmarshallerTKhaiXml = new GenericObjectPool<>(new UnmarshallerPoolFactory(TKhaiDLXml.class));
//        ObjectPool<Marshaller> marshallerTKhaiXml = new GenericObjectPool<>(new MarshallerPoolFactory(TKhaiDLXml.class));;
//        tkhaiDLService = new TKhaiDLService(unmarshallerTKhaiXml, marshallerTKhaiXml);
    }

    @Test
    void testInstant() throws ParseException {
        String data= "10/06/2022T10:00:00";
        SimpleDateFormat SDF_PATTERN_1 = new SimpleDateFormat("dd/MM/yyyy'T'HH:mm:ss");

        String data1 = SDF_PATTERN_1.format(new Date());


        Instant now = new Date().toInstant().truncatedTo(ChronoUnit.DAYS);
        System.out.println(now);
    }

    @Test
    void testImportTKhaiXml() throws JAXBValidationException {
        String xml = "<TKhai>\n" +
                "\t<DLTKhai>\n" +
                "\t\t<TTChung>\n" +
                "\t\t\t<PBan>2.0.0</PBan>\n" +
                "\t\t\t<MSo>03/DL-HĐĐT</MSo>\n" +
                "\t\t\t<Ten>TK dữ liệu hóa đơn, chứng từ hàng hóa, dịch vụ bán ra theo quy định tại Nghị định 123/2020/NĐ-CP</Ten>\n" +
                "\t\t\t<LKTThue>N</LKTThue>\n" +
                "\t\t\t<KTThue>01/06/2022</KTThue>\n" +
                "\t\t\t<STKhai>1</STKhai>\n" +
                "\t\t\t<DDanh>Hà Nội</DDanh>\n" +
                "\t\t\t<NLap>2022-06-15T10:58:29</NLap>\n" +
                "\t\t\t<TNNT>Nguyễn Văn A</TNNT>\n" +
                "\t\t\t<MST>0123456789</MST>\n" +
                "\t\t\t<TDLThue>Đại lý thuế Cầu Giấy</TDLThue>\n" +
                "\t\t\t<MSTDLThue>9876543210</MSTDLThue>\n" +
                "\t\t\t<DVTTe>VNĐ</DVTTe>\n" +
                "\t\t</TTChung>\n" +
                "\t\t<NDTKhai>\n" +
                "\t\t\t<TTSuat>\n" +
                "\t\t\t\t<TSuat>10%</TSuat>\n" +
                "\t\t\t\t<DSHDon>\n" +
                "\t\t\t\t\t<HDon>\n" +
                "\t\t\t\t\t\t<STT>1</STT>\n" +
                "\t\t\t\t\t\t<KHMSHDon>1</KHMSHDon>\n" +
                "\t\t\t\t\t\t<KHHDon>C22TAA</KHHDon>\n" +
                "\t\t\t\t\t\t<SHDon>1</SHDon>\n" +
                "\t\t\t\t\t\t<NLap>2022-06-15T10:58:29</NLap>\n" +
                "\t\t\t\t\t\t<TNMua>Người Mua Duy Nhất</TNMua>\n" +
                "\t\t\t\t\t\t<MSTNMua>0000000000</MSTNMua>\n" +
                "\t\t\t\t\t\t<DTCThue>8000</DTCThue>\n" +
                "\t\t\t\t\t\t<TGTGT>800</TGTGT>\n" +
                "\t\t\t\t\t\t<GChu>Hóa đơn 1</GChu>\n" +
                "\t\t\t\t\t</HDon>\n" +
                "\t\t\t\t\t<HDon>\n" +
                "\t\t\t\t\t\t<STT>2</STT>\n" +
                "\t\t\t\t\t\t<KHMSHDon>1</KHMSHDon>\n" +
                "\t\t\t\t\t\t<KHHDon>C22TAA</KHHDon>\n" +
                "\t\t\t\t\t\t<SHDon>2</SHDon>\n" +
                "\t\t\t\t\t\t<NLap>2022-06-15T10:58:29</NLap>\n" +
                "\t\t\t\t\t\t<TNMua>Người Mua Duy Nhất Còn Lại</TNMua>\n" +
                "\t\t\t\t\t\t<MSTNMua>1111111111</MSTNMua>\n" +
                "\t\t\t\t\t\t<DTCThue>2000</DTCThue>\n" +
                "\t\t\t\t\t\t<TGTGT>200</TGTGT>\n" +
                "\t\t\t\t\t\t<GChu>Hóa đơn 2</GChu>\n" +
                "\t\t\t\t\t</HDon>\n" +
                "\t\t\t\t</DSHDon>\n" +
                "\t\t\t\t<TgDTCThue>10000</TgDTCThue>\n" +
                "\t\t\t\t<TgTGTGT>1000</TgTGTGT>\n" +
                "\t\t\t</TTSuat>\n" +
                "\t\t\t<TTSuat>\n" +
                "\t\t\t\t<TSuat>5%</TSuat>\n" +
                "\t\t\t\t<DSHDon>\n" +
                "\t\t\t\t\t<HDon>\n" +
                "\t\t\t\t\t\t<STT>1</STT>\n" +
                "\t\t\t\t\t\t<KHMSHDon>1</KHMSHDon>\n" +
                "\t\t\t\t\t\t<KHHDon>C22TAA</KHHDon>\n" +
                "\t\t\t\t\t\t<SHDon>1</SHDon>\n" +
                "\t\t\t\t\t\t<NLap>2022-06-15T10:58:29</NLap>\n" +
                "\t\t\t\t\t\t<TNMua>Người Mua Duy Nhất</TNMua>\n" +
                "\t\t\t\t\t\t<MSTNMua>0000000000</MSTNMua>\n" +
                "\t\t\t\t\t\t<DTCThue>10000</DTCThue>\n" +
                "\t\t\t\t\t\t<TGTGT>500</TGTGT>\n" +
                "\t\t\t\t\t\t<GChu>Hóa đơn 1</GChu>\n" +
                "\t\t\t\t\t</HDon>\n" +
                "\t\t\t\t\t<HDon>\n" +
                "\t\t\t\t\t\t<STT>2</STT>\n" +
                "\t\t\t\t\t\t<KHMSHDon>1</KHMSHDon>\n" +
                "\t\t\t\t\t\t<KHHDon>C22TAA</KHHDon>\n" +
                "\t\t\t\t\t\t<SHDon>2</SHDon>\n" +
                "\t\t\t\t\t\t<NLap>2022-06-15T10:58:29</NLap>\n" +
                "\t\t\t\t\t\t<TNMua>Người Mua Duy Nhất Còn Lại</TNMua>\n" +
                "\t\t\t\t\t\t<MSTNMua>1111111111</MSTNMua>\n" +
                "\t\t\t\t\t\t<DTCThue>20000</DTCThue>\n" +
                "\t\t\t\t\t\t<TGTGT>1000</TGTGT>\n" +
                "\t\t\t\t\t\t<GChu>Hóa đơn 2</GChu>\n" +
                "\t\t\t\t\t</HDon>\n" +
                "\t\t\t\t</DSHDon>\n" +
                "\t\t\t\t<TgDTCThue>30000</TgDTCThue>\n" +
                "\t\t\t\t<TgTGTGT>1500</TgTGTGT>\n" +
                "\t\t\t</TTSuat>\n" +
                "\t\t\t<TgDThu>40000</TgDThu>\n" +
                "\t\t\t<TgThue>2500</TgThue>\n" +
                "\t\t</NDTKhai>\n" +
                "\t</DLTKhai>\n" +
                "</TKhai>\n";

        TKhaiDLXml tkhaiDLXml = tkhaiDLService.toTKhaiDLObject(xml);
        log.info(tkhaiDLXml.toString());

        String xml1 = tkhaiDLService.toTKhaiDLXml(tkhaiDLXml);
        log.info(xml1);
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
