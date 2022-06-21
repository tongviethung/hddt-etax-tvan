package vn.teca.hddt.etax.tvan.xml.validator;

import org.apache.commons.lang3.StringUtils;
import vn.teca.hddt.etax.tvan.util.RegexCommon;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TaxRateConstraintValidator implements ConstraintValidator<TaxRateConstraint, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) return true;
        return RegexCommon.validTaxRate(value);
    }
}
