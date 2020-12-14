package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.exception.IllegalRequestDataException;

/**
 * @author Panfilov Dmitriy
 * 14.12.2020
 */
@Component
public class UserFormValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserTo.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserTo user = (UserTo) o;
        String email = ((UserTo) o).getEmail();
      //  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", errors.getFieldError().toString());
        if (!email.equals("")) {
            if (userService.getByEmail(email) != null) {

              //  errors.rejectValue("email", "!!!!!!!");

            }
        }
    }
}
