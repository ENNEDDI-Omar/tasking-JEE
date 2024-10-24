package org.tasking.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.tasking.domain.entities.User;
import org.tasking.ejb.RoleEJB;
import org.tasking.ejb.UserEJB;
import org.tasking.ejb.TaskEJB;
import org.tasking.ejb.TagEJB;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.tasking.domain.entities.Task;
import org.tasking.domain.entities.Tag;
import org.tasking.domain.entities.Role;
import org.tasking.exceptions.TaskException;

import java.io.IOException;
@WebServlet(urlPatterns = {
        "/manager/dashboard",
        "/manager/users",
        "/manager/users/*",
        "/manager/tasks",
        "/manager/tasks/*",
        "/manager/tags",
        "/manager/tags/*"
})
public class ManagerServlet extends HttpServlet {
    @EJB
    private UserEJB userEJB;

    @EJB
    private RoleEJB RoleEJB;

    @EJB
    private TaskEJB taskEJB;

    @EJB
    private TagEJB tagEJB;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        // Vérifier si l'utilisateur est un manager
        if (!isManager(currentUser)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {
            switch (servletPath) {
                case "/manager/dashboard":
                    showDashboard(request, response);
                    break;
                case "/manager/users":
                    handleUsersGet(request, response, pathInfo);
                    break;
                case "/manager/tasks":
                    handleTasksGet(request, response, pathInfo);
                    break;
                case "/manager/tags":
                    handleTagsGet(request, response, pathInfo);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error/500.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        if (!isManager(currentUser)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {
            switch (servletPath) {
                case "/manager/users":
                    handleUsersPost(request, response, pathInfo);
                    break;
                case "/manager/tasks":
                    handleTasksPost(request, response, pathInfo);
                    break;
                case "/manager/tags":
                    handleTagsPost(request, response, pathInfo);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error/500.jsp").forward(request, response);
        }
    }

    // Méthodes de gestion Users
    private void handleUsersGet(HttpServletRequest request, HttpServletResponse response, String pathInfo)
            throws ServletException, IOException {
        if (pathInfo == null) {
            showUsers(request, response);
        } else if (pathInfo.equals("/create")) {
            showCreateUserForm(request, response);
        } else if (pathInfo.startsWith("/edit/")) {
            Long userId = Long.parseLong(pathInfo.substring(6));
            showEditUserForm(request, response, userId);
        }
    }

    private void handleUsersPost(HttpServletRequest request, HttpServletResponse response, String pathInfo)
            throws ServletException, IOException {
        if (pathInfo == null || pathInfo.equals("/create")) {
            createUser(request, response);
        } else if (pathInfo.startsWith("/edit/")) {
            Long userId = Long.parseLong(pathInfo.substring(6));
            updateUser(request, response, userId);
        } else if (pathInfo.startsWith("/delete/")) {
            Long userId = Long.parseLong(pathInfo.substring(8));
            deleteUser(request, response, userId);
        }
    }

    // Méthodes de gestion Tasks
    private void handleTasksGet(HttpServletRequest request, HttpServletResponse response, String pathInfo)
            throws ServletException, IOException {
        if (pathInfo == null) {
            showTasks(request, response);
        } else if (pathInfo.equals("/create")) {
            showCreateTaskForm(request, response);
        } else if (pathInfo.startsWith("/edit/")) {
            Long taskId = Long.parseLong(pathInfo.substring(6));
            showEditTaskForm(request, response, taskId);
        }
    }

    private void handleTasksPost(HttpServletRequest request, HttpServletResponse response, String pathInfo)
            throws ServletException, IOException {
        if (pathInfo == null || pathInfo.equals("/create")) {
            createTask(request, response);
        } else if (pathInfo.startsWith("/edit/")) {
            Long taskId = Long.parseLong(pathInfo.substring(6));
            updateTask(request, response, taskId);
        } else if (pathInfo.startsWith("/delete/")) {
            Long taskId = Long.parseLong(pathInfo.substring(8));
            deleteTask(request, response, taskId);
        }
    }

    // Méthodes de gestion Tags
    private void handleTagsGet(HttpServletRequest request, HttpServletResponse response, String pathInfo)
            throws ServletException, IOException {
        if (pathInfo == null) {
            showTags(request, response);
        } else if (pathInfo.equals("/create")) {
            showCreateTagForm(request, response);
        } else if (pathInfo.startsWith("/edit/")) {
            Long tagId = Long.parseLong(pathInfo.substring(6));
            showEditTagForm(request, response, tagId);
        }
    }

    private void handleTagsPost(HttpServletRequest request, HttpServletResponse response, String pathInfo)
            throws ServletException, IOException {
        if (pathInfo == null || pathInfo.equals("/create")) {
            createTag(request, response);
        } else if (pathInfo.startsWith("/edit/")) {
            Long tagId = Long.parseLong(pathInfo.substring(6));
            updateTag(request, response, tagId);
        } else if (pathInfo.startsWith("/delete/")) {
            Long tagId = Long.parseLong(pathInfo.substring(8));
            deleteTag(request, response, tagId);
        }
    }

    // Méthodes d'affichage
    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("users", userEJB.findAllUsers());
        request.setAttribute("tasks", taskEJB.getAllTasks());
        request.setAttribute("tags", tagEJB.findAllTags());
        request.getRequestDispatcher("/WEB-INF/views/manager/dashboard.jsp").forward(request, response);
    }

    private void showUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("users", userEJB.findAllUsers());
        request.getRequestDispatcher("/WEB-INF/views/manager/users/index.jsp").forward(request, response);
    }

    private void showCreateUserForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("roles", userEJB.getAllRoles());
        request.getRequestDispatcher("/WEB-INF/views/manager/users/create.jsp").forward(request, response);
    }

    private void showEditUserForm(HttpServletRequest request, HttpServletResponse response, Long userId)
            throws ServletException, IOException {
        User user = userEJB.findUserById(userId);
        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        request.setAttribute("user", user);
        request.setAttribute("roles", userEJB.getAllRoles());
        request.getRequestDispatcher("/WEB-INF/views/manager/edit.jsp").forward(request, response);
    }

    private void showTasks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("tasks", taskEJB.getAllTasks());
        request.getRequestDispatcher("/WEB-INF/views/manager/tasks/index.jsp").forward(request, response);
    }

    private void showCreateTaskForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        List<User> users = userEJB.findAllUsers();
        System.out.println("Sending users to view: " + users.size());

        request.setAttribute("users", users);
        request.setAttribute("tags", tagEJB.findAllTags());
        request.getRequestDispatcher("/WEB-INF/views/manager/tasks/create.jsp").forward(request, response);
    }

    private void showEditTaskForm(HttpServletRequest request, HttpServletResponse response, Long taskId)
            throws ServletException, IOException {
        Task task = taskEJB.findTaskById(taskId);
        if (task == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        request.setAttribute("task", task);
        request.setAttribute("users", userEJB.findAllUsers());
        request.setAttribute("tags", tagEJB.findAllTags());
        request.getRequestDispatcher("/WEB-INF/views/manager/tasks/edit.jsp").forward(request, response);
    }

    private void showTags(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("tags", tagEJB.findAllTags());
        request.getRequestDispatcher("/WEB-INF/views/manager/tags/tags.jsp").forward(request, response);
    }

    private void showCreateTagForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/manager/tags/create.jsp").forward(request, response);
    }

    private void showEditTagForm(HttpServletRequest request, HttpServletResponse response, Long tagId)
            throws ServletException, IOException {
        Tag tag = tagEJB.findTagById(tagId);
        if (tag == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        request.setAttribute("tag", tag);
        request.getRequestDispatcher("/WEB-INF/views/manager/tags/edit.jsp").forward(request, response);
    }

    // Méthodes de gestion des Users
    private void createUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String roleId = request.getParameter("roleId");

        try {
            User user = new User(username, email, password);
            Role role = RoleEJB.findRoleById(Long.parseLong(roleId));
            user.setRole(role);
            userEJB.createUser(user);
            response.sendRedirect(request.getContextPath() + "/manager/users");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            showCreateUserForm(request, response);
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response, Long userId)
            throws ServletException, IOException {
        try {
            User user = userEJB.findUserById(userId);
            if (user == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            user.setUsername(request.getParameter("username"));
            user.setEmail(request.getParameter("email"));
            if (request.getParameter("password") != null && !request.getParameter("password").isEmpty()) {
                user.setPassword(request.getParameter("password")); // Assurez-vous de hasher le mot de passe
            }
            Long roleId = Long.parseLong(request.getParameter("roleId"));
            Role role = RoleEJB.findRoleById(roleId);
            user.setRole(role);

            userEJB.updateUser(user);
            response.sendRedirect(request.getContextPath() + "/manager/users");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            showEditUserForm(request, response, userId);
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response, Long userId)
            throws ServletException, IOException {
        try {
            userEJB.deleteUser(userId);
            response.sendRedirect(request.getContextPath() + "/manager/users");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
        }
    }

    private void createTask(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Récupérer l'utilisateur courant en premier
            HttpSession session = request.getSession();
            User currentUser = (User) session.getAttribute("user");
            if (currentUser == null) {
                throw new TaskException("Session expirée");
            }

            // Créer la tâche
            Task task = new Task();
            task.setTitle(request.getParameter("title"));
            task.setDescription(request.getParameter("description"));
            task.setDueDate(LocalDateTime.parse(request.getParameter("dueDate")));

            // Utilisateur assigné
            Long assignedUserId = Long.parseLong(request.getParameter("assignedUserId"));
            User assignedUser = userEJB.findUserById(assignedUserId);
            if (assignedUser == null) {
                throw new TaskException("L'utilisateur assigné n'existe pas");
            }
            task.setAssignedUser(assignedUser);

            // Gestion des tags
            String[] tagIds = request.getParameterValues("tags");
            if (tagIds == null || tagIds.length < 2) {
                throw new TaskException("Au moins 2 tags sont requis");
            }

            List<Tag> tags = new ArrayList<>();
            for (String tagId : tagIds) {
                Tag tag = tagEJB.findTagById(Long.parseLong(tagId));
                if (tag != null) {
                    tags.add(tag);
                }
            }
            task.setTags(tags);

            // Créer la tâche
            taskEJB.createTask(task, currentUser);
            request.getSession().setAttribute("successMessage", "Tâche créée avec succès!");
            response.sendRedirect(request.getContextPath() + "/manager/tasks");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            request.setAttribute("users", userEJB.findAllUsers());
            request.setAttribute("tags", tagEJB.findAllTags());
            request.getRequestDispatcher("/WEB-INF/views/manager/tasks/create.jsp").forward(request, response);
        }
    }

    private void updateTask(HttpServletRequest request, HttpServletResponse response, Long taskId)
            throws ServletException, IOException {
        try {
            Task task = taskEJB.findTaskById(taskId);
            if (task == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            task.setTitle(request.getParameter("title"));
            task.setDescription(request.getParameter("description"));
            task.setDueDate(LocalDateTime.parse(request.getParameter("dueDate")));

            Long assignedUserId = Long.parseLong(request.getParameter("assignedUserId"));
            User assignedUser = userEJB.findUserById(assignedUserId);
            task.setAssignedUser(assignedUser);

            // Gérer les tags
            String[] tagIds = request.getParameterValues("tags");
            Set<Tag> tags = new HashSet<>();
            if (tagIds != null) {
                for (String tagId : tagIds) {
                    Tag tag = tagEJB.findTagById(Long.parseLong(tagId));
                    if (tag != null) {
                        tags.add(tag);
                    }
                }
            }
            task.setTags((List<Tag>) tags);

            taskEJB.updateTask(task);
            response.sendRedirect(request.getContextPath() + "/manager/tasks");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            showEditTaskForm(request, response, taskId);
        }
    }

    private void deleteTask(HttpServletRequest request, HttpServletResponse response, Long taskId)
            throws ServletException, IOException {
        try {
            taskEJB.deleteTaskById(taskId);
            response.sendRedirect(request.getContextPath() + "/manager/tasks");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
        }
    }

    // Méthodes de gestion des Tags
    private void createTag(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Tag tag = new Tag();
            tag.setName(request.getParameter("name"));
            tag.setColor(request.getParameter("color"));

            tagEJB.createTag(tag);
            response.sendRedirect(request.getContextPath() + "/manager/tags");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            showCreateTagForm(request, response);
        }
    }

    private void updateTag(HttpServletRequest request, HttpServletResponse response, Long tagId)
            throws ServletException, IOException {
        try {
            Tag tag = tagEJB.findTagById(tagId);
            if (tag == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            tag.setName(request.getParameter("name"));
            tag.setColor(request.getParameter("color"));

            tagEJB.updateTag(tag);
            response.sendRedirect(request.getContextPath() + "/manager/tags");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            showEditTagForm(request, response, tagId);
        }
    }

    private void deleteTag(HttpServletRequest request, HttpServletResponse response, Long tagId)
            throws ServletException, IOException {
        try {
            tagEJB.deleteTag(tagId);
            response.sendRedirect(request.getContextPath() + "/manager/tags");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
        }
    }

    private boolean isManager(User user) {
        return user != null && "MANAGER".equals(user.getRole().getName());
    }
}


