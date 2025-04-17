package app.project.jjoojjeollee.param.user;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = {DuplicatedEmailValidator.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface DuplicatedEmailConstraint {
    String message() default "Duplicated Email";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
