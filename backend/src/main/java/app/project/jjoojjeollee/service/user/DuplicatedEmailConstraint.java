package app.project.jjoojjeollee.service.user;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = {DuplicatedEmailValidator.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface DuplicatedEmailConstraint {
    String message() default "중복된 이메일";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
