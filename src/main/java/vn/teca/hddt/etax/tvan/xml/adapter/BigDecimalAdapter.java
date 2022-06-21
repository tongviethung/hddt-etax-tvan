package vn.teca.hddt.etax.tvan.xml.adapter;

import org.apache.commons.lang3.StringUtils;
import vn.teca.hddt.etax.tvan.exception.JAXBValidationException;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class BigDecimalAdapter extends XmlAdapter<String, BigDecimal> {
    public static final class BigDecimal2Adapter extends BigDecimalAdapter {
        public BigDecimal2Adapter() {
            super("#0.00");
        }
    }

    public static final class BigDecimal4Adapter extends BigDecimalAdapter {
        public BigDecimal4Adapter() {
            super("#0.0000");
        }
    }

    public static final class BigDecimal6Adapter extends BigDecimalAdapter {
        public BigDecimal6Adapter() {
            super("#0.000000");
        }
    }

    private final ThreadLocal<DecimalFormat> format;

    public BigDecimalAdapter(String pattern) {
        format = ThreadLocal.withInitial(() -> {
            DecimalFormat df = new DecimalFormat(pattern);
            df.setRoundingMode(RoundingMode.HALF_UP);
            return df;
        });
    }

    /**
     * Overrides the unmarshal method of the class XmlAdapter in order
     * to parse BigDecimal of the type String
     *
     * @param s String
     * @return BigDecimal
     */
    @Override
    public BigDecimal unmarshal(String s) throws JAXBValidationException {
        if (StringUtils.isBlank(s)) return null;
        try {
            return new BigDecimal(s);
        } catch (Exception ex) {
            throw new JAXBValidationException("Lỗi chuyển đổi dữ liệu sang định dạng số: " + s);
        }
    }

    /**
     * Overrides the unmarshal method of the class XmlAdapter in order
     * to convert the passed date to an String
     *
     * @param bigDecimal BigDecimal
     * @return String
     */
    @Override
    public String marshal(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return null;
        }
        String fmt = format.get().format(bigDecimal);
        format.remove();
        return !fmt.contains(".") ? fmt : fmt.replaceAll("0*$", "").replaceAll("\\.$", "");
    }
}
