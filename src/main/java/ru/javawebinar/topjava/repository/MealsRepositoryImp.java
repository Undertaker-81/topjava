package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;
import java.time.Month;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;


public class MealsRepositoryImp implements MealsRepository{
    private AtomicLong id = new AtomicLong();
    private List<Meal> meals;
    public MealsRepositoryImp(){
        meals = new CopyOnWriteArrayList<>();
         meals.add( new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500) );
          meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000))  ;
          meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500))  ;
          meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
          meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
          meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.add( new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
        meals.forEach(meal -> meal.setId(id.incrementAndGet()));
    }

    @Override
    public List<Meal> getAll() {
        return meals;
    }

    @Override
    public void create(Meal meal) {
        meal.setId(id.incrementAndGet());
        meals.add(meal);
    }

    @Override
    public void edit(long id, LocalDateTime dateTime, String description, int calories) {
        Meal meal;
        if (getMealById(id) != null){
           meal = getMealById(id);
           meal.setDateTime(dateTime);
           meal.setDescription(description);
           meal.setCalories(calories);
        }

    }

    @Override
    public void delete(long id) {
        if (getMealById(id) != null){
            meals.remove(getMealById(id));
        }

    }
    public Meal getMealById (long id){
        for (Meal meal : meals){
            if (meal.getId() == id)
                return meal;
        }
        return null;
    }
}
