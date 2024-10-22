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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @EJB
    private AuthEJB authEJB;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getUserPrincipal() != null) {
            String role = request.isUserInRole("MANAGER") ? "manager" : "user";
            String redirectUrl = role.equals("manager") ? "/manager/dashboard" : "/user/tasks";
            response.sendRedirect(request.getContextPath() + redirectUrl);
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
    }


}