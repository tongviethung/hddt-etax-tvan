package vn.teca.hddt.etax.tvan.xml.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = BigDecimalConstraintValidator.class)
public @interface BigDecimalConstraint {
    String message() default "{javax.validation.constraints.BigDecimalConstraint.message}";

    long totalDigit();

    int fraction();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
