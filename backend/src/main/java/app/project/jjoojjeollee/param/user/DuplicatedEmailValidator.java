package app.project.jjoojjeollee.param.user;

import app.project.jjoojjeollee.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class DuplicatedEmailValidator implements ConstraintValidator<DuplicatedEmailConstraint, String> {

    @Autowired private UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null || value.isEmpty()) {
            return false;
        } else {
            return userRepository.findByEmail(value).isEmpty();
        }
    }
}
