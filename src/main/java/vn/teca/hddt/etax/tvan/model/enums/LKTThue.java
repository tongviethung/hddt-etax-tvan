package vn.teca.hddt.etax.tvan.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;
import java.util.stream.Stream;

public enum LKTThue {
    T("T"),
    Q("Q"),
    N("N");

    private final String value;

    LKTThue(String value) {
        this.value = value;
    }

    @JsonCreator
    public static LKTThue fromValue(final String value) {
        return Stream.of(LKTThue.values()).filter(targetEnum -> Objects.equals(targetEnum.value, value)).findFirst().orElse(null);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
