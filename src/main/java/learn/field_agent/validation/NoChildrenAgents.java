package learn.field_agent.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = {NoChildValidator.class}) // 3
@Documented
public @interface NoChildrenAgents {

    String message() default "{Agents under twelve years of age are not allowed}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

