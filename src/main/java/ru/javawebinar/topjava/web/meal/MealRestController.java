package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    @Autowired
    private MealService service;

    public Meal get(int id){
        log.info("get {}", id);
        return   service.get(id, SecurityUtil.authUserId());
    }
    public void delete(int id){
        log.info("delete {}", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal){
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, SecurityUtil.authUserId());
    }

    public void update(Meal meal, int id){

        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal,SecurityUtil.authUserId());
    }

    public Collection<Meal> getAll(){
        log.info("getAll");
        return service.getAll(SecurityUtil.authUserId());
    }
    public List<MealTo> getFilteredByDate(String strStartDate, String strEndDate, String strStartTime, String strEndTime) {
        if (strStartDate.equals("") && strEndDate.equals("") && strStartTime.equals("") && strEndTime.equals("")) {
            return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
        }

        if (strStartDate.equals("") && strEndDate.equals("")) {
            if (strStartTime.equals("")) {
                LocalTime endTime = LocalTime.parse(strEndTime, TIME_FORMATTER);
                return service.getFilteredByTime(SecurityUtil.authUserId(), LocalTime.MIN, endTime, SecurityUtil.authUserCaloriesPerDay());
            } else if (strEndTime.equals("")) {
                LocalTime startTime = LocalTime.parse(strStartTime, TIME_FORMATTER);
                return service.getFilteredByTime(SecurityUtil.authUserId(), startTime, LocalTime.MAX, SecurityUtil.authUserCaloriesPerDay());

            } else {
                LocalTime startTime = LocalTime.parse(strStartTime, TIME_FORMATTER);
                LocalTime endTime = LocalTime.parse(strEndTime, TIME_FORMATTER);
                return service.getFilteredByTime(SecurityUtil.authUserId(), startTime, endTime, SecurityUtil.authUserCaloriesPerDay());

            }
        }
        if (strStartTime.equals("") && strEndTime.equals("")) {
            if (strStartDate.equals("")) {
                LocalDate endDate = LocalDate.parse(strEndDate, DATE_FORMATTER);
                return service.getFilteredByDate(SecurityUtil.authUserId(), LocalDate.MIN, endDate, SecurityUtil.authUserCaloriesPerDay());
            } else if (strEndDate.equals("")) {
                LocalDate startDate = LocalDate.parse(strStartDate, DATE_FORMATTER);
                return service.getFilteredByDate(SecurityUtil.authUserId(), startDate, LocalDate.MAX, SecurityUtil.authUserCaloriesPerDay());

            } else {
                LocalDate startDate = LocalDate.parse(strStartDate, DATE_FORMATTER);
                LocalDate endDate = LocalDate.parse(strEndDate, DATE_FORMATTER);
                return service.getFilteredByDate(SecurityUtil.authUserId(), startDate, endDate, SecurityUtil.authUserCaloriesPerDay());

            }
        }
        if (strStartDate.equals("") & !strEndDate.equals("") & strStartTime.equals("") & !strEndTime.equals("")) {
            LocalDate endDate = LocalDate.parse(strEndDate, DATE_FORMATTER);
            LocalTime endTime = LocalTime.parse(strEndTime, TIME_FORMATTER);
            return service.getFilteredByDate(SecurityUtil.authUserId(),
                    LocalDate.MIN, endDate, SecurityUtil.authUserCaloriesPerDay()).stream()
                    .filter(mealTo -> DateTimeUtil.isBetweenHalfOpenDateTime(mealTo.getDateTime().toLocalTime(), LocalTime.MIN, endTime))
                    .collect(Collectors.toList());

        }
        if (!strStartDate.equals("") & strEndDate.equals("") & !strStartTime.equals("") & strEndTime.equals("")) {
            LocalDate startDate = LocalDate.parse(strStartDate, DATE_FORMATTER);
            LocalTime startTime = LocalTime.parse(strStartTime, TIME_FORMATTER);
            return service.getFilteredByDate(SecurityUtil.authUserId(), startDate,
                    LocalDate.MAX, SecurityUtil.authUserCaloriesPerDay()).stream()
                    .filter(mealTo -> DateTimeUtil.isBetweenHalfOpenDateTime(mealTo.getDateTime().toLocalTime(), startTime, LocalTime.MAX))
                    .collect(Collectors.toList());

        }
        if (!strStartDate.equals("") & strEndDate.equals("") & strStartTime.equals("") & !strEndTime.equals("")) {
            LocalDate startDate = LocalDate.parse(strStartDate, DATE_FORMATTER);
            LocalTime endTime = LocalTime.parse(strEndTime, TIME_FORMATTER);
            return service.getFilteredByDate(SecurityUtil.authUserId(), startDate,
                    LocalDate.MAX, SecurityUtil.authUserCaloriesPerDay()).stream()
                    .filter(mealTo -> DateTimeUtil.isBetweenHalfOpenDateTime(mealTo.getDateTime().toLocalTime(), LocalTime.MIN, endTime))
                    .collect(Collectors.toList());
        }
        if (strStartDate.equals("") & !strEndDate.equals("") & !strStartTime.equals("") & strEndTime.equals("")) {
            LocalDate endDate = LocalDate.parse(strEndDate, DATE_FORMATTER);
            LocalTime startTime = LocalTime.parse(strStartTime, TIME_FORMATTER);
            return service.getFilteredByDate(SecurityUtil.authUserId(),
                    LocalDate.MIN, endDate, SecurityUtil.authUserCaloriesPerDay()).stream()
                    .filter(mealTo -> DateTimeUtil.isBetweenHalfOpenDateTime(mealTo.getDateTime().toLocalTime(), startTime, LocalTime.MAX))
                    .collect(Collectors.toList());
        }
        LocalDate startDate = LocalDate.parse(strStartDate, DATE_FORMATTER);
        LocalDate endDate = LocalDate.parse(strEndDate, DATE_FORMATTER);
        LocalTime startTime = LocalTime.parse(strStartTime, TIME_FORMATTER);
        LocalTime endTime = LocalTime.parse(strEndTime, TIME_FORMATTER);
        return service.getFilteredByDate(SecurityUtil.authUserId(), startDate, endDate, SecurityUtil.authUserCaloriesPerDay())
                                        .stream()
                                        .filter(mealTo -> DateTimeUtil.isBetweenHalfOpenDateTime(mealTo.getDateTime().toLocalTime(), startTime, endTime))
                                        .collect(Collectors.toList());
    }
}