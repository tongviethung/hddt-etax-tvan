package vn.teca.hddt.etax.tvan.xml;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;
import org.eclipse.persistence.oxm.annotations.XmlPath;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "TDiep")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"pban", "mngui", "mnnhan", "mltdiep", "mtdiep", "mtdtchieu", "mloi", "mta", "hsgoc"})
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlNullPolicy(emptyNodeRepresentsNull = true)
public class TDiepKHLXml {
    @XmlPath("TTChung/PBan/text()")
//    @NotNull
//    @Size(max = 6)
    private String pban;

    @XmlPath("TTChung/MNGui/text()")
    @NotNull
//    @Size(max = 14)
    private String mngui;

    @XmlPath("TTChung/MNNhan/text()")
//    @Size(max = 14)
    private String mnnhan;

    @XmlPath("TTChung/MLTDiep/text()")
    private Short mltdiep;

    @XmlPath("TTChung/MTDiep/text()")
//    @Size(max = 46)
    private String mtdiep;

    @XmlPath("TTChung/MTDTChieu/text()")
//    @Size(max = 46)
    private String mtdtchieu;

    @XmlElement(name = "DLieu/MLoi")
    @NotNull
    private String mloi;

    @XmlElement(name = "DLieu/MTa")
    @NotNull
    private String mta;

    @XmlElement(name = "DLieu/HSGoc")
    @NotNull
    private String hsgoc;
}
