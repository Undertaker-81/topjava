package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealsRepository;
import ru.javawebinar.topjava.repository.MealsRepositoryImp;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static org.slf4j.LoggerFactory.getLogger;

public class MealsServlet extends HttpServlet {
    private static final Logger log = getLogger(MealsServlet.class);
    private MealsRepository repository;
    private static final DateTimeFormatter INPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @Override
    public void init() throws ServletException {
        this.repository = new MealsRepositoryImp();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String mealId = request.getParameter("mealid");

        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("date"), INPUT_DATE_FORMAT);
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        if (mealId == null || mealId.isEmpty()){
            repository.create(new Meal(dateTime, description, calories));
            log.debug("create meal");
        }
        else {
            long id = Long.parseLong(mealId);
            repository.edit(id, dateTime, description, calories);
        }
        doGet(request, response);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            List<MealTo> mealTos = MealsUtil.filteredByStreams(repository.getAll(), LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
            request.setAttribute("meals", mealTos);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
        if (action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            repository.delete(mealId);

            List<MealTo> mealTos = MealsUtil.filteredByStreams(repository.getAll(), LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
            request.setAttribute("meals", mealTos);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
        if (action.equalsIgnoreCase("edit")) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));

            request.setAttribute("mealid", mealId);
            request.getRequestDispatcher("/createMeal.jsp").forward(request, response);
        }

    }
}