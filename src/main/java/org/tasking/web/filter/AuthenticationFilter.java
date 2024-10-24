package org.tasking.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.tasking.domain.entities.User;
import jakarta.ejb.EJB;
import org.tasking.ejb.UserEJB;

import java.io.IOException;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        String requestURI = httpRequest.getRequestURI();

        // Autoriser l'accès à la page d'accueil
        if (requestURI.equals(httpRequest.getContextPath() + "/") ||
                requestURI.equals(httpRequest.getContextPath() + "/index.jsp") ||
                isPublicResource(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        // Vérifier l'authentification pour les autres pages
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");

            // Vérification des accès selon le rôle
            if (requestURI.contains("/manager/") && !user.getRole().getName().equals("MANAGER")) {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            chain.doFilter(request, response);
        } else if (!isPublicResource(requestURI)) {
            // Rediriger vers la page de login
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean isPublicResource(String requestURI) {
        return requestURI.endsWith("/login") ||
                requestURI.endsWith("/register") ||
                requestURI.contains("/resources/") ||
                requestURI.contains("/ressources/") ||
                requestURI.contains("/error/") ||
                requestURI.endsWith(".css") ||
                requestURI.endsWith(".js") ||
                requestURI.endsWith(".jpg") ||
                requestURI.endsWith(".png");
    }
}