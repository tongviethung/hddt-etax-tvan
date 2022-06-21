package vn.teca.hddt.etax.tvan.xml.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import java.util.stream.Stream;

@XmlEnum
public enum LKTThueXml {
    @XmlEnumValue("T") T("T"),
    @XmlEnumValue("Q") Q("Q"),
    @XmlEnumValue("N") N("N");

    private final String value;

    LKTThueXml(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static LKTThueXml fromValue(String value) {
        return Stream.of(LKTThueXml.values())
                .filter(targetEnum -> targetEnum.value.equals(value))
                .findFirst().orElse(null);
    }
}
