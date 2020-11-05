package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Repository

public class DataJpaMealRepository implements MealRepository {

    private final CrudMealRepository crudRepository;

    public DataJpaMealRepository(CrudMealRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()){

            meal.setUser(crudRepository.geUserbyId(userId));
           return crudRepository.save(meal);
        }else {
            Meal mealNew = get(meal.getId(), userId);
            if (mealNew != null){
                mealNew.setCalories(meal.getCalories());
                mealNew.setDescription(meal.getDescription());
                mealNew.setDateTime(meal.getDateTime());
                if (crudRepository.update(mealNew.getDateTime(),
                        mealNew.getCalories(),
                        mealNew.getDescription(),
                        mealNew.getId(),
                        userId) != 0) {
                    return mealNew;
            }



            }
        }
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.delete(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudRepository.getMealByIdAndUserId(id, userId);

    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.getAllByUserIdOrderByDateTimeDesc(userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudRepository.getAllByUserIdAndDateTimeGreaterThanEqualAndDateTimeBeforeOrderByDateTimeDesc(userId, startDateTime, endDateTime);
    }
}
