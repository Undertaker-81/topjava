package ru.javawebinar.topjava.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * @author Dmitriy Panfilov
 * 15.12.2020
 */
@Component
public class UniqueDataTimeConstraintValidator implements ConstraintValidator<ValidateDataTime, Meal> {

    @Autowired
    private MealService mealService;

    @Autowired
    private ReloadableResourceBundleMessageSource resourceBundle;

    public void initialize(ValidateDataTime constraint) {
    }

    public boolean isValid(Meal meal, ConstraintValidatorContext context) {

        List<LocalDateTime> mealsDateTime = mealService.getAll(SecurityUtil.authUserId())
                                                                        .stream()
                                                                        .map(Meal::getDateTime)
                                                                        .collect(Collectors.toList());

        if (mealsDateTime.contains(meal.getDateTime()) && meal.isNew()){
            String message = resourceBundle.getMessage("error.meal.data",null, Locale.getDefault());
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                                .addPropertyNode("dateTime")
                                .addConstraintViolation();
            return false;
        }
        return true;
    }
}
