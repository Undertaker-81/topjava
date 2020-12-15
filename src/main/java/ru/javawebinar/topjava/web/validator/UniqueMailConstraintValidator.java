package ru.javawebinar.topjava.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.exception.IllegalRequestDataException;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.validator.ValidateMail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.PropertyResourceBundle;

/**
 * @author Dmitriy Panfilov
 * 15.12.2020
 */
@Component
public class UniqueMailConstraintValidator implements ConstraintValidator<ValidateMail, UserTo> {

    @Autowired
    private UserService userService;

   @Autowired
   private ReloadableResourceBundleMessageSource resourceBundle;


    public void initialize(ValidateMail constraint) {
    }

    @Override
    public boolean isValid(UserTo userTo, ConstraintValidatorContext constraintValidatorContext) {
        String message = resourceBundle.getMessage("error.user.emailDouble",null,null);
        User user = null;
        try {
            user = userService.getByEmail(userTo.getEmail());
        }catch (NotFoundException e){

        }
        if (user != null) {
            if (user.getEmail().equals(userTo.getEmail())){
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                        .addPropertyNode("email")
                        .addConstraintViolation();
                    return false;
            }

        }
        return true;
    }


}
