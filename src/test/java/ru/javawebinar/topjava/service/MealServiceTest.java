package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.Util;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.UserTestData.NOT_FOUND;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.util.DateTimeUtil.atStartOfNextDayOrMax;

/**
 * @author Dmitriy Panfilov
 * 18.10.2020
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal actual = service.get(MealTestData.MEAL_ID, UserTestData.USER_ID);
        Meal expected = MealTestData.getMeal(MealTestData.MEAL_ID);
        MealTestData.assertMatch(actual, expected);
    }

    @Test
    public void delete() {
        service.delete( MealTestData.MEAL_ID,UserTestData.USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(UserTestData.USER_ID, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        LocalDateTime startDate = LocalDateTime.of(2020, Month.JANUARY, 30,0,0,0);
        LocalDateTime endDate = LocalDateTime.of(2020, Month.JANUARY, 30,23,59,59);
        List<Meal> between = MealTestData.meals.stream()
                                                        .filter(meal -> Util.isBetweenHalfOpen(meal.getDateTime(), startDate, endDate))
                                                        .collect(Collectors.toList());
        Collections.reverse(between);
        List<Meal> actual = service.getBetweenInclusive(startDate.toLocalDate(), endDate.toLocalDate(), USER_ID);
        assertThat(actual).isEqualTo(between);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(UserTestData.USER_ID);
        List<Meal> expected = MealTestData.meals;
        Collections.reverse(expected);
        assertThat(all).isEqualTo(expected);
    }

    @Test
    public void update() {
        Meal updated = MealTestData.getUpdated(MealTestData.MEAL_ID);
        service.update(updated, USER_ID);
        MealTestData.assertMatch(service.get(MealTestData.MEAL_ID, USER_ID), updated);
    }

    @Test
    public void create() {
        Meal newMeal = MealTestData.getNew();
        Meal created = service.create(newMeal, UserTestData.USER_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        MealTestData.assertMatch(created, newMeal);
        MealTestData.assertMatch(service.get(newId, UserTestData.USER_ID), newMeal);
    }

    @Test
    public void getAlen() {

        assertThrows(NotFoundException.class, () -> service.get(MealTestData.MEAL_ID, NOT_FOUND));
    }

    @Test
    public void deleteAlen() {

        assertThrows(NotFoundException.class, () -> service.delete( MealTestData.MEAL_ID, NOT_FOUND));
    }

    @Test
    public void updateAlen() {
        Meal updated = MealTestData.getUpdated(MealTestData.MEAL_ID);
        assertThrows(NotFoundException.class, () -> service.update( updated, NOT_FOUND));
    }
}