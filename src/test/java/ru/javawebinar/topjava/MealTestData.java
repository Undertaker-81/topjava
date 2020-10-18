package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

/**
 * @author Dmitriy Panfilov
 * 18.10.2020
 */
public class MealTestData {
    public static final int MEAL_ID = START_SEQ + 2;
    public static final List<Meal> meals = Arrays.asList(
            new Meal(MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(MEAL_ID + 1,LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(MEAL_ID + 2,LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(MEAL_ID + 3,LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(MEAL_ID + 4,LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(MEAL_ID + 5,LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(MEAL_ID + 6,LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );

    public static Meal getNew(){
        return new Meal(null, LocalDateTime.now(), "новая еда", 650);
    }
    public static Meal getUpdated(Integer id){
        Meal meal = meals.get(id);
        Meal updated = new Meal(meal);
        updated.setCalories(600);
        updated.setDescription("updated");
        return updated;
    }
    public static Meal getMeal(int id){
        for (Meal meal : meals){
            if (meal.getId() == id)
                return meal;
        }
        return null;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("date_time").isEqualTo(expected);
    }
}
