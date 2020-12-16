package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Optional;


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
        UserTo userTo = (UserTo) o;
        String email = userTo.getEmail();
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.user.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.user.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.user.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "caloriesPerDay", "error.user.caloriesPerDay");
        User user = null;
        User authorizeduser = null;
        try {

             user = userService.getByEmail(email);
            Integer id = SecurityUtil.authUserId();
             authorizeduser = userService.get(id);
        }catch (Exception e){

        }

         if (user != null) {
            if (user.getEmail().equals(email) && !user.equals(authorizeduser))
              errors.rejectValue("email", "error.user.emailDouble");


         }
         if (userTo.getPassword().length() < 5){
             errors.rejectValue("password", "error.user.passwordLength");

         }

    }
}
