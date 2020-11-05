package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.Collections;
import java.util.List;

import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.MealTestData.*;

/**
 * @author Dmitriy Panfilov
 * 31.10.2020
 */
@ActiveProfiles({"datajpa","postgres"})
public class UserServiceDataJpaTest extends UserServiceTest{

    @Autowired
    private UserService service;
    @Test
    public void getMealsByUserId() {
        List<Meal> all = service.get(USER_ID).getMeals();
        Collections.reverse(all);
        MEAL_MATCHER.assertMatch(all, meals);
    }
}
