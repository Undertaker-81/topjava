package ru.javawebinar.topjava.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.exception.IllegalRequestDataException;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.validator.ValidateMail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Locale;
import java.util.PropertyResourceBundle;

/**
 * @author Dmitriy Panfilov
 * 15.12.2020
 */
@Component
public class UniqueMailConstraintValidator implements ConstraintValidator<ValidateMail, Object> {

    @Autowired
    private UserService userService;

   @Autowired
   private ReloadableResourceBundleMessageSource resourceBundle;


    public void initialize(ValidateMail constraint) {
    }

    @Override
    public boolean isValid(Object user, ConstraintValidatorContext constraintValidatorContext) {

        String email = "";
        Boolean isNew = null;
        User userExist = null;
        if (user instanceof UserTo ){
            UserTo   created = (UserTo) user;
             email = created.getEmail();
             isNew = created.isNew();
        }
        if (user instanceof User ){
            User   created = (User) user;
            email = created.getEmail();
            isNew = created.isNew();
        }

        try {
            userExist = userService.getByEmail(email);

        }catch (Exception e){

        }
        if (userExist != null) {

            if (userExist.getEmail().equals(email) && isNew ){
                String message = resourceBundle.getMessage("error.user.emailDouble",null, Locale.getDefault());
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
