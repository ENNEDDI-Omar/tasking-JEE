package org.tasking.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.tasking.domain.entities.User;
import org.tasking.domain.entities.Role;
import org.tasking.ejb.UserEJB;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

@WebServlet("/users/*")
public class TestUserServlet extends HttpServlet {
    @EJB
    private UserEJB userEJB;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/list";
        }

        switch (action) {
            case "/list":
                listUsers(request, response);
                break;
            case "/add":
                showAddForm(request, response);
                break;
            case "/edit":
               // showEditForm(request, response);
                break;
            case "/delete":
               // deleteUser(request, response);
                break;
            default:
                listUsers(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/list";
        }

        switch (action) {
            case "/add":
                addUser(request, response);
                break;
            case "/edit":
                //updateUser(request, response);
                break;
            default:
                listUsers(request, response);
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = userEJB.findAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/views/user/list.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/user/form.jsp").forward(request, response);
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        Role role = new Role("USER"); // Simplification, vous devriez gérer les rôles correctement

        User newUser = new User(username, email, password, role);
        userEJB.createUser(newUser);

        response.sendRedirect(request.getContextPath() + "/users/list");
    }

    // Ajoutez les méthodes pour showEditForm, updateUser, et deleteUser de manière similaire
}