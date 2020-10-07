package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;
import java.util.List;

public interface MealsRepository {

    List<Meal> getAll();

    void create(Meal meal);

    void edit(long id, LocalDateTime dateTime, String description, int calories);

    void delete(long id);

    Meal getMealById (long id);

}
