package org.tasking.web.servlet.auth;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import org.tasking.ejb.AuthEJB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet
{
    @EJB
    private AuthEJB authUserEJB;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        try {
            authUserEJB.registerUser(username, email, password);
            response.sendRedirect(request.getContextPath() + "/login");
        } catch (Exception e) {
            request.setAttribute("error", "Registration failed: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
        }
    }

}
