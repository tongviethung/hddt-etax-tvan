package vn.teca.hddt.etax.tvan.xml;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;
import org.eclipse.persistence.oxm.annotations.XmlPath;
import vn.teca.hddt.etax.tvan.xml.adapter.DateAdapter;
import vn.teca.hddt.etax.tvan.xml.enums.TTTNhanXml;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.List;

@XmlRootElement(name = "TBao")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlNullPolicy(emptyNodeRepresentsNull = true)
public class TBaoPhanHoiXml {
    @XmlElement(name = "MTDiep")
    @Size(max = 46)
    private String mtdiep;

    @XmlElement(name = "MNGui")
    @Size(max = 14)
    private String mngui;

    @XmlElement(name = "NNhan")
    @XmlJavaTypeAdapter(DateAdapter.class)
    @NotNull
    private Date nnhan;

    @XmlElement(name = "TTTNhan")
    @NotNull
    private TTTNhanXml tttnhan;

    @XmlPath("DSLDo/LDo")
    private List<RejectedReason> dsldkhle;

    @XmlRootElement(name = "LDo")
    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RejectedReason {
        @XmlPath("MLoi/text()")
        private String mloi;
        @XmlPath("MTa/text()")
        private String mta;
    }
}

