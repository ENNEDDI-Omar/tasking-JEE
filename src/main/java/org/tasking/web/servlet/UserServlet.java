package org.tasking.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.tasking.domain.entities.Task;
import org.tasking.domain.entities.User;
import org.tasking.domain.entities.Role;
import org.tasking.ejb.RoleEJB;
import org.tasking.ejb.UserEJB;
import org.tasking.ejb.TaskEJB; // Ajout de l'import
import java.io.IOException;
import java.util.List;

@WebServlet("/user/*")
public class UserServlet extends HttpServlet {
    @EJB
    private UserEJB userEJB;

    @EJB
    private TaskEJB taskEJB;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        switch (action) {
            case "/profile":
                showProfile(request, response);
                break;
            case "/tasks":
                showUserTasks(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(request, response);
    }

    private void showUserTasks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        List<Task> tasks = taskEJB.getTasksByUser(user);
        request.setAttribute("tasks", tasks);
        request.getRequestDispatcher("/WEB-INF/views/user/tasks.jsp").forward(request, response);
    }
}