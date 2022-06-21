package vn.teca.hddt.etax.tvan.xml;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;
import org.eclipse.persistence.oxm.annotations.XmlPath;
import vn.teca.hddt.etax.tvan.xml.adapter.BigDecimalAdapter;
import vn.teca.hddt.etax.tvan.xml.adapter.DateAdapter;
import vn.teca.hddt.etax.tvan.xml.enums.LKTThueXml;
import vn.teca.hddt.etax.tvan.xml.validator.BigDecimalConstraint;
import vn.teca.hddt.etax.tvan.xml.validator.TaxRateConstraint;
import vn.teca.hddt.etax.tvan.xml.validator.TinConstraint;

import javax.validation.Valid;
import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@XmlRootElement(name = "TKhai")
@XmlAccessorType(XmlAccessType.FIELD)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlNullPolicy(emptyNodeRepresentsNull = true)
public class TKhaiDLXml {

    //region TTChung

    @XmlPath("DLTKhai/TTChung/PBan/text()")
    @NotBlank
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @Size(max = 6)
    private String pban;

    @XmlPath("DLTKhai/TTChung/MSo/text()")
    @NotBlank
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @Size(max = 15)
    private String mso;

    @XmlPath("DLTKhai/TTChung/Ten/text()")
    @NotBlank
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @Size(max = 100)
    private String ten;

    @XmlPath("DLTKhai/TTChung/LKTThue/text()")
    @NotNull
    private LKTThueXml lktthue;

    @XmlPath("DLTKhai/TTChung/KTThue/text()")
    @NotBlank
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @Size(max = 10)
    private String ktthue;

    @XmlPath("DLTKhai/TTChung/STKhai/text()")
    @NotNull
    @Min(value = 1)
    @Max(value = 9999)
    private Integer stkhai;

    @XmlPath("DLTKhai/TTChung/DDanh/text()")
    @NotBlank
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @Size(max = 50)
    private String ddanh;

    @XmlPath("DLTKhai/TTChung/NLap/text()")
    @NotNull
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date nlap;

    @XmlPath("DLTKhai/TTChung/TNNT/text()")
    @NotBlank
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @Size(max = 400)
    private String tnnt;

    @XmlPath("DLTKhai/TTChung/MST/text()")
    @NotBlank
    @Size(max = 14)
    @TinConstraint
    private String mst;

    @XmlPath("DLTKhai/TTChung/TDLThue/text()")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @Size(max = 400)
    private String tdlthue;

    @XmlPath("DLTKhai/TTChung/MSTDLThue/text()")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @Size(max = 14)
    @TinConstraint
    private String mstdlthue;

    @XmlPath("DLTKhai/TTChung/DVTTe/text()")
    @NotBlank
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @Size(max = 3)
    private String dvtte;

    //endregion

    //region HDon

    @XmlRootElement(name = "HDon")
    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HDonXml {
        @XmlPath("STT/text()")
        @Min(value = 1)
        @Max(value = 999999)
        private Integer stt;

        @XmlPath("KHMSHDon/text()")
        @NotBlank
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @Size(max = 11)
        private String khmshdon;

        @XmlPath("KHHDon/text()")
        @NotBlank
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @Size(max = 8)
        private String khhdon;

        // todo Xem lại kiểu dữ liệu ở đây
        @XmlPath("SHDon/text()")
        @NotBlank
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @Size(max = 8)
        private String shdon;

        @XmlPath("NLap/text()")
        @NotNull
        @XmlJavaTypeAdapter(DateAdapter.class)
        private Date nlap;

        @XmlPath("TNMua/text()")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @Size(max = 400)
        private String tnmua;

        @XmlPath("MSTNMua/text()")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @Size(max = 14)
        @TinConstraint
        private String mstnmua;

        @XmlPath("DTCThue/text()")
        @NotNull
        @BigDecimalConstraint(totalDigit = 21, fraction = 6)
        @XmlJavaTypeAdapter(BigDecimalAdapter.BigDecimal6Adapter.class)
        private BigDecimal dtcthue;

        @XmlPath("TGTGT/text()")
        @NotNull
        @BigDecimalConstraint(totalDigit = 21, fraction = 6)
        @XmlJavaTypeAdapter(BigDecimalAdapter.BigDecimal6Adapter.class)
        private BigDecimal tgtgt;

        @XmlPath("GChu/text()")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @Size(max = 255)
        private String gchu;
    }

    //endregion

    //region TTSuat

    @XmlRootElement(name = "TTSuat")
    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TTSuatXml {
        @XmlPath("TSuat/text()")
        @Size(max = 11)
        @NotBlank
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @TaxRateConstraint
        private String tsuat;

        @Valid
        @XmlPath("DSHDon/HDon")
        private List<HDonXml> hdon;

        @XmlPath("TgDTCThue/text()")
        @NotNull
        @BigDecimalConstraint(totalDigit = 21, fraction = 6)
        @XmlJavaTypeAdapter(BigDecimalAdapter.BigDecimal6Adapter.class)
        private BigDecimal tgdtcthue;

        @XmlPath("TgTGTGT/text()")
        @NotNull
        @BigDecimalConstraint(totalDigit = 21, fraction = 6)
        @XmlJavaTypeAdapter(BigDecimalAdapter.BigDecimal6Adapter.class)
        private BigDecimal tgtgtgt;
    }

    //endregion

    @Valid
    @XmlPath("DLTKhai/NDTKhai/TTSuat")
    private List<TTSuatXml> ttsuat;

    @XmlPath("DLTKhai/NDTKhai/TgDThu/text()")
    @NotNull
    @BigDecimalConstraint(totalDigit = 21, fraction = 6)
    @XmlJavaTypeAdapter(BigDecimalAdapter.BigDecimal6Adapter.class)
    private BigDecimal tgdthu;

    @XmlPath("DLTKhai/NDTKhai/TgThue/text()")
    @NotNull
    @BigDecimalConstraint(totalDigit = 21, fraction = 6)
    @XmlJavaTypeAdapter(BigDecimalAdapter.BigDecimal6Adapter.class)
    private BigDecimal tgthue;

    @XmlTransient
    private String mcqtqly;

    @XmlTransient
    private String msgid;
}
