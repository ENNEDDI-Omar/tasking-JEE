package org.tasking.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.tasking.domain.entities.Task;
import org.tasking.ejb.TaskEJB;

import java.io.IOException;
import java.util.List;

@WebServlet("/tasks")
public class TaskServlet extends HttpServlet {
    @EJB
    private TaskEJB taskEJB;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Task> tasks = taskEJB.getAllTasks();
        request.setAttribute("tasks", tasks);
        request.getRequestDispatcher("/WEB-INF/views/tasks.jsp").forward(request, response);
    }
}
