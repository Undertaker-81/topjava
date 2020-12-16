package ru.javawebinar.topjava.web.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * @author Dmitriy Panfilov
 * 15.12.2020
 */
@Documented
@Constraint(validatedBy = UniqueMailConstraintValidator.class)
@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateMail {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
