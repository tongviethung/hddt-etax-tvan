package vn.teca.hddt.etax.tvan.mapper;

import org.apache.commons.lang.StringUtils;
import vn.teca.hddt.etax.tvan.model.TKhaiCTiet;
import vn.teca.hddt.etax.tvan.model.TKhaiDL;
import vn.teca.hddt.etax.tvan.model.enums.LKTThue;
import vn.teca.hddt.etax.tvan.xml.TKhaiDLXml;
import vn.teca.hddt.etax.tvan.xml.enums.LKTThueXml;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TKhaiDLMapper {

    private static String decodeXmlString(String input) {
        return input == null ? null : input
                .replace("&amp;", "&")
                .replace("&gt;", ">")
                .replace("&lt;", "<");
    }

    private static Double convertTsuat(String source) {
        if (StringUtils.isNotBlank(source)) {
            switch (source) {
                case "0%":
                case "KCT":
                case "KKKNT":
                    return 0.0;
                case "5%":
                    return 0.05;
                case "8%":
                    return 0.08;
                case "10%":
                    return 0.1;
                default:
                    try {
                        if (source.startsWith("KHAC:")) {
                            BigDecimal bd = BigDecimal.valueOf(Double.parseDouble(source.substring(5).replace("%", "")));
                            BigDecimal result = bd.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
                            return result.doubleValue();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
            }
        }
        return 0.0;
    }

    private static String convertLtsuat(String source) {
        if (StringUtils.isNotBlank(source)) {
            if (source.startsWith("KHAC:")) {
                return "KHAC";
            }
            return source;
        }
        return null;
    }

    public static TKhaiDL fromXml(TKhaiDLXml entity) {
        TKhaiDL result = new TKhaiDL();
        result.setLktthue(entity.getLktthue() == null ? null :
                LKTThue.fromValue(entity.getLktthue().getValue()));
        result.setKtthue(entity.getKtthue());
        result.setStkhai(entity.getStkhai());
        result.setPban(entity.getPban());
        result.setMso(entity.getMso());
        result.setTen(decodeXmlString(entity.getTen()));
        result.setDdanh(decodeXmlString(entity.getDdanh()));
        // trường này chỉ quan tâm ngày tháng nên set thời gian về 00h
        result.setNlap(entity.getNlap() == null ? null :
                entity.getNlap().toInstant());
        result.setTnnt(decodeXmlString(entity.getTnnt()));
        result.setMst(entity.getMst());
        result.setTdlthue(decodeXmlString(entity.getTdlthue()));
        result.setMstdlthue(entity.getMstdlthue());
        result.setDvtte(entity.getDvtte());
        result.setTgdthu(entity.getTgdthu() == null ? null : entity.getTgdthu().doubleValue());
        result.setTgthue(entity.getTgthue() == null ? null : entity.getTgthue().doubleValue());
        result.setNtao(Instant.now());
        result.setMsgid(entity.getMsgid());

        List<TKhaiCTiet> tkdlctiets = new ArrayList<>();
        if(entity.getTtsuat() != null){
            for(TKhaiDLXml.TTSuatXml ttsuat: entity.getTtsuat()){
                if(ttsuat.getHdon() != null){
                    for(TKhaiDLXml.HDonXml invoice: ttsuat.getHdon()){
                        TKhaiCTiet detail = new TKhaiCTiet();
                        detail.setMst(result.getMst());
                        detail.setLktthue(result.getLktthue());
                        detail.setKtthue(result.getKtthue());
                        detail.setStkhai(result.getStkhai());
                        detail.setTsuatgoc(ttsuat.getTsuat());
                        detail.setId(UUID.randomUUID());
                        detail.setStt(invoice.getStt());
                        detail.setKhmshdon(invoice.getKhmshdon());
                        detail.setKhhdon(invoice.getKhhdon());
                        detail.setShdon(invoice.getShdon());
                        detail.setNlap(invoice.getNlap() == null ? null : invoice.getNlap().toInstant());
                        detail.setTnmua(decodeXmlString(invoice.getTnmua()));
                        detail.setMstnmua(invoice.getMstnmua());
                        detail.setDtcthue(invoice.getDtcthue() == null ? null : invoice.getDtcthue().doubleValue());
                        detail.setTgtgt(invoice.getTgtgt() == null ? null : invoice.getTgtgt().doubleValue());
                        detail.setGchu(decodeXmlString(invoice.getGchu()));
                        detail.setTgdtcthue(ttsuat.getTgdtcthue() == null ? null : ttsuat.getTgdtcthue().doubleValue());
                        detail.setTgtgtgt(ttsuat.getTgtgtgt() == null ? null : ttsuat.getTgtgtgt().doubleValue());
                        detail.setNtao(Instant.now());
                        detail.setTsuat(convertTsuat(ttsuat.getTsuat()));
                        detail.setLtsuat(convertLtsuat(ttsuat.getTsuat()));
                        detail.setMcqtqly(entity.getMcqtqly());
                        detail.setMsgid(entity.getMsgid());
                        tkdlctiets.add(detail);
                    }
                }
            }
        }
        result.setTkdlctiets(tkdlctiets);
        return result;
    }

    public static TKhaiDLXml toXml(TKhaiDL entity) {
        TKhaiDLXml result = new TKhaiDLXml();
        result.setPban(entity.getPban());
        result.setMso(entity.getMso());
        result.setTen(entity.getTen());
        result.setLktthue(entity.getLktthue() == null ? null : LKTThueXml.fromValue(entity.getLktthue().getValue()));
        result.setKtthue(entity.getKtthue());
        result.setStkhai(entity.getStkhai());
        result.setDdanh(entity.getDdanh());
        result.setNlap(entity.getNlap() == null ? null : Date.from(entity.getNlap()));
        result.setTnnt(entity.getTnnt());
        result.setMst(entity.getMst());
        result.setTdlthue(entity.getTdlthue());
        result.setMstdlthue(entity.getMstdlthue());
        result.setDvtte(entity.getDvtte());
        result.setTgdthu(entity.getTgdthu() == null ? null : BigDecimal.valueOf(entity.getTgdthu()));
        result.setTgthue(entity.getTgthue() == null ? null : BigDecimal.valueOf(entity.getTgthue()));

        // todo: bổ sung xuất xml phần chi tiết thuế suất sau nếu cần

        return result;
    }


}
