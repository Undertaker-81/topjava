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
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealsServlet extends HttpServlet {
    private static final Logger log = getLogger(MealsServlet.class);
    private MealsRepository repository;

    @Override
    public void init() throws ServletException {
        this.repository = new MealsRepositoryImp();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MealTo> mealTos = MealsUtil.filteredByStreams(repository.getAll(), LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
        request.setAttribute("meals", mealTos);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
