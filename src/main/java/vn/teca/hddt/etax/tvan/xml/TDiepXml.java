package vn.teca.hddt.etax.tvan.xml;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.persistence.oxm.annotations.XmlNullPolicy;
import org.eclipse.persistence.oxm.annotations.XmlPath;
import vn.teca.hddt.etax.tvan.xml.handler.DLieuElementHandler;
import vn.teca.hddt.etax.tvan.xml.validator.TDiepConstraint;

import javax.validation.Valid;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "TDiep")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"ttchung", "dlieu"})
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@TDiepConstraint
@XmlNullPolicy(emptyNodeRepresentsNull = true)
public class TDiepXml {
    @Valid
    @XmlPath("TTChung")
    private TTChungXml ttchung;
    @XmlAnyElement(value = DLieuElementHandler.class)
    private String dlieu;
}
