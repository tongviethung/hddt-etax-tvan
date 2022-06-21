package vn.teca.hddt.etax.tvan.xml.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import java.util.stream.Stream;

@XmlEnum
public enum TTTNhanXml {
    @XmlEnumValue("0") THANH_CONG((byte) 0),
    @XmlEnumValue("1") LOI((byte) 1);

    private final Byte value;

    TTTNhanXml(Byte value) {
        this.value = value;
    }

    public Byte getValue() {
        return this.value;
    }

    public static TTTNhanXml fromValue(Byte value) {
        return Stream.of(TTTNhanXml.values())
                .filter(targetEnum -> targetEnum.value.equals(value))
                .findFirst().orElse(null);
    }
}