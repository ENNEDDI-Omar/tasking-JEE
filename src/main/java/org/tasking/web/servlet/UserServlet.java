package org.tasking.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.tasking.domain.entities.User;
import org.tasking.domain.entities.Task;
import org.tasking.ejb.TaskEJB;
import org.tasking.ejb.UserEJB;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {
        "/user/tasks",
        "/user/profile",
        "/user/task/*"  // Pour les opérations sur une tâche spécifique
})
public class UserServlet extends HttpServlet {
    @EJB
    private UserEJB userEJB;

    @EJB
    private TaskEJB taskEJB;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String servletPath = request.getServletPath();
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        switch (servletPath) {
            case "/user/tasks":
                showTasks(request, response, currentUser);
                break;
            case "/user/profile":
                showProfile(request, response, currentUser);
                break;
            case "/user/task":
                if (pathInfo != null) {
                    switch (pathInfo) {
                        case "/create":
                            //showCreateTaskForm(request, response);
                            break;
                        case "/edit":
                            //showEditTaskForm(request, response);
                            break;
                        default:
                            response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    }
                }
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String servletPath = request.getServletPath();
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        switch (servletPath) {
            case "/user/task":
                if (pathInfo != null) {
                    switch (pathInfo) {
                        case "/create":
                            //createTask(request, response, currentUser);
                            break;
                        case "/edit":
                            //updateTask(request, response, currentUser);
                            break;
                        case "/delete":
                            //deleteTask(request, response, currentUser);
                            break;
                        default:
                            response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    }
                }
                break;
            case "/user/profile":
                //updateProfile(request, response, currentUser);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showTasks(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        List<Task> tasks = taskEJB.getTasksByUser(user);
        request.setAttribute("tasks", tasks);
        request.getRequestDispatcher("/WEB-INF/views/user/tasks.jsp").forward(request, response);
    }

    private void showProfile(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        request.setAttribute("userProfile", user);
        request.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(request, response);
    }

    // Ajoutez les autres méthodes selon vos besoins
}