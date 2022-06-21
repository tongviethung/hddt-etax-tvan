package vn.teca.hddt.etax.tvan.codec;


import com.datastax.oss.driver.api.core.type.codec.MappingCodec;
import com.datastax.oss.driver.api.core.type.codec.TypeCodecs;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;
import edu.umd.cs.findbugs.annotations.Nullable;
import vn.teca.hddt.etax.tvan.model.enums.EtaxTTXLy;

public class EtaxTTXLyCodec extends MappingCodec<Byte, EtaxTTXLy> {

    public EtaxTTXLyCodec() {
        super(TypeCodecs.TINYINT, GenericType.of(EtaxTTXLy.class));
    }

    @Nullable
    @Override
    protected EtaxTTXLy innerToOuter(@Nullable Byte value) {
        return value == null ? null : EtaxTTXLy.fromValue(value);
    }

    @Nullable
    @Override
    protected Byte outerToInner(@Nullable EtaxTTXLy value) {
        return value == null ? null : value.getValue();
    }
}