package vn.teca.hddt.etax.tvan.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum EtaxTTXLy {
    RECEIVED((byte) 0),
    VALID((byte) 1),
    INVALID((byte) 2);

    private final byte value;

    EtaxTTXLy(byte value) {
        this.value = value;
    }

    @JsonCreator
    public static EtaxTTXLy fromValue(final byte value) {
        return Stream.of(EtaxTTXLy.values()).filter(targetEnum -> targetEnum.value == value).findFirst().orElse(null);
    }

    @JsonValue
    public byte getValue() {
        return value;
    }
}
