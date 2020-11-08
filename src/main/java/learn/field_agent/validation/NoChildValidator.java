package learn.field_agent.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class NoChildValidator implements ConstraintValidator<NoChildrenAgents, LocalDate> { // 1

    @Override
    public void initialize(NoChildrenAgents constraintAnnotation) { }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        if (date != null && date.isAfter(LocalDate.now().minusYears(12))) {
            return false;
        }
        return true;
    }
}
