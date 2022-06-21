package vn.teca.hddt.etax.tvan.xml.validator;

import org.apache.commons.lang3.StringUtils;
import vn.teca.hddt.etax.tvan.util.RegexCommon;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TinConstraintValidator implements ConstraintValidator<TinConstraint, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) return true;
        return RegexCommon.validTin(value);
    }
}
