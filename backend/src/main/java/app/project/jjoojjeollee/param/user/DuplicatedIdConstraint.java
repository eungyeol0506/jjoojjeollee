package app.project.jjoojjeollee.param.user;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = {DuplicatedIdValidator.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface DuplicatedIdConstraint {
    String message() default "중복된 아이디";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
