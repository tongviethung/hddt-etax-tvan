package vn.teca.hddt.etax.tvan.codec;


import com.datastax.oss.driver.api.core.type.codec.MappingCodec;
import com.datastax.oss.driver.api.core.type.codec.TypeCodecs;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;
import edu.umd.cs.findbugs.annotations.Nullable;
import vn.teca.hddt.etax.tvan.model.enums.LKTThue;

public class LKTThueCodec extends MappingCodec<String, LKTThue> {

    public LKTThueCodec() {
        super(TypeCodecs.TEXT, GenericType.of(LKTThue.class));
    }

    @Nullable
    @Override
    protected LKTThue innerToOuter(@Nullable String value) {
        return value == null ? null : LKTThue.fromValue(value);
    }

    @Nullable
    @Override
    protected String outerToInner(@Nullable LKTThue value) {
        return value == null ? null : value.getValue();
    }
}