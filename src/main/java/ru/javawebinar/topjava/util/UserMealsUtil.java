package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

      //  List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
       // mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));

      //  System.out.println(isUserMealExcess(meals.get(6).getDateTime(), meals, 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        return null;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
      return   meals.stream()
                            .map(userMeal -> new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), isUserMealExcess(userMeal.getDateTime(), meals, caloriesPerDay)))
                            .filter(userMealWithExcess -> TimeUtil.isBetweenHalfOpen(userMealWithExcess.getDateTime().toLocalTime(), startTime, endTime))
                            .collect(Collectors.toList());

    }

    public static Boolean isUserMealExcess(LocalDateTime userMealDateTime, List<UserMeal> meals, int caloriesPerDay){
        int sum = 0;
       sum =
               meals.stream()
                        .filter(userMeal -> userMeal.getDateTime().getDayOfYear() == userMealDateTime.getDayOfYear() && userMeal.getDateTime().getYear() == userMealDateTime.getYear())
                        .filter(userMeal -> userMeal.getDateTime().isBefore(userMealDateTime) || userMeal.getDateTime().isEqual(userMealDateTime))
                       // .forEach(System.out::println);
                        .mapToInt(userMeal -> userMeal.getCalories())
                        .sum();
        return sum > caloriesPerDay;
    }
}
