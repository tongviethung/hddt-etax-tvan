package vn.teca.hddt.etax.tvan.xml;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;
import org.eclipse.persistence.oxm.annotations.XmlPath;
import vn.teca.hddt.etax.tvan.xml.validator.TinConstraint;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "TTChung")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"pban", "mngui", "mnnhan", "mltdiep", "mtdiep", "mtdtchieu", "mst", "sluong"})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlNullPolicy(emptyNodeRepresentsNull = true)
public class TTChungXml {
    @XmlPath("PBan/text()")
    @NotBlank
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @Size(max = 6)
    private String pban;

    @XmlPath("MNGui/text()")
    @NotBlank
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @Size(max = 14)
    private String mngui;

    @XmlPath("MNNhan/text()")
    @NotBlank
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @Size(max = 14)
    private String mnnhan;

    @XmlPath("MLTDiep/text()")
    @NotNull
    private Integer mltdiep;

    @XmlPath("MTDiep/text()")
    @NotBlank
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @Size(max = 46)
    private String mtdiep;

    @XmlPath("MTDTChieu/text()")
    @Size(max = 46)
    private String mtdtchieu;

    @XmlPath("MST/text()")
    @Size(max = 14)
//    @NotBlank
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @TinConstraint
    private String mst;

    @XmlPath("SLuong/text()")
//    @NotNull
    @Min(value = 1)
    @Max(value = 9999999)
    private Integer sluong;
}
