package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id and m.user.id=:userid")
    int delete(@Param("id") int id, @Param("userid") int userId);

    Meal getMealByIdAndUserId(int id, int userId);

    List<Meal> getAllByUserIdOrderByDateTimeDesc(int userId);

    List<Meal> getAllByUserIdAndDateTimeGreaterThanEqualAndDateTimeBeforeOrderByDateTimeDesc(int userId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Transactional
    @Modifying
    @Query("UPDATE Meal m SET m.dateTime = :datetime, m.calories= :calories, m.description=:desc where m.id=:id and m.user.id=:userId")
    int update (@Param("datetime") LocalDateTime dateTime,
                @Param("calories") int calories,
                @Param("desc") String description,
                @Param("id") int id,
                @Param("userId") int userId);

    @Query("SELECT  u FROM User u WHERE u.id =:id")
    User geUserbyId(@Param("id") int id);


}
