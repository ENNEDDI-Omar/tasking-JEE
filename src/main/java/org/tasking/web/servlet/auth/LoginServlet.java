package org.tasking.web.servlet.auth;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.tasking.ejb.AuthEJB;
import org.tasking.domain.entities.User;

import java.io.IOException;

@WebServlet("/servlet/auth/LoginServlet")
public class LoginServlet extends HttpServlet {

    @EJB
    private AuthEJB authEJB;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Afficher la page de login
        request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = authEJB.authenticateUser(username, password);

            // Authentification réussie
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Rediriger vers la page d'accueil ou le tableau de bord
            response.sendRedirect(request.getContextPath() + "/servlet/TaskServlet");
        } catch (Exception e) {
            // Authentification échouée
            request.setAttribute("errorMessage", "Invalid username or password");
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
        }
    }
}