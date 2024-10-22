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

    @EJB
    private UserEJB userEJB;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String requestURI = httpRequest.getRequestURI();

        // Si c'est une ressource publique, laissez passer
        if (isPublicResource(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        // Vérifiez si l'utilisateur est authentifié
        if (httpRequest.getUserPrincipal() != null) {
            // Si l'utilisateur est dans la session, laissez passer
            if (session != null && session.getAttribute("user") != null) {
                chain.doFilter(request, response);
                return;
            }

            // Si l'utilisateur n'est pas dans la session, récupérez-le et stockez-le
            String username = httpRequest.getUserPrincipal().getName();
            User user = userEJB.findUserByUsername(username);

            if (user != null) {
                // Stockez l'utilisateur dans la session
                if (session == null) {
                    session = httpRequest.getSession(true);
                }
                session.setAttribute("user", user);

                // Si c'est la page de login ou la racine, redirigez vers la page appropriée
                if (requestURI.endsWith("/login") || requestURI.equals(httpRequest.getContextPath() + "/")) {
                    String redirectUrl = determineRedirectUrl(user);
                    httpResponse.sendRedirect(httpRequest.getContextPath() + redirectUrl);
                    return;
                }
            }
        }

        chain.doFilter(request, response);
    }

    private boolean isPublicResource(String requestURI) {
        return requestURI.contains("/resources/") ||
                requestURI.endsWith("/login") ||
                requestURI.endsWith("/register") ||
                requestURI.contains("/error/") ||
                requestURI.endsWith(".css") ||
                requestURI.endsWith(".js") ||
                requestURI.endsWith(".jpg") ||
                requestURI.endsWith(".png");
    }

    private String determineRedirectUrl(User user) {
        if ("MANAGER".equals(user.getRole().getName())) {
            return "/manager/dashboard";
        } else {
            return "/user/tasks";
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}