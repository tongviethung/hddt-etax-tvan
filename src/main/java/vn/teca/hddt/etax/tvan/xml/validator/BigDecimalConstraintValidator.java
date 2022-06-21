package vn.teca.hddt.etax.tvan.xml.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class BigDecimalConstraintValidator implements ConstraintValidator<BigDecimalConstraint, BigDecimal> {

    protected long totalDigit;

    protected long fraction;

    @Override
    public void initialize(BigDecimalConstraint constraintAnnotation) {
        this.totalDigit = constraintAnnotation.totalDigit();
        this.fraction = constraintAnnotation.fraction();
    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        if (value == null) return true;
        String plainString = value.stripTrailingZeros().abs().toPlainString();
        int index = plainString.indexOf(".");
        String fractionPart = index >= 0 ? plainString.substring(index + 1) : "";

        if (fractionPart.length() > fraction) return false;
        if (index >= 0) {
            return plainString.length() - 1 <= totalDigit;
        } else {
            return plainString.length() <= totalDigit;
        }
    }
}
