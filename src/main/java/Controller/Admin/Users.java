package Controller.Admin;

import model.bean.User;
import model.bo.AdminBO;
import model.bo.UserBO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/UserManage/*")
public class Users extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AdminBO adminBO = new AdminBO();
    private UserBO userBO = new UserBO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User loggedInUser = (User) req.getSession().getAttribute("user");

        if (loggedInUser == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("Unauthorized access");
            return;
        }

        String pathInfo = req.getPathInfo();

        if (loggedInUser.getRole().equals("admin")) {
            if (pathInfo == null || pathInfo.equals("/")) {
                String username = req.getParameter("username");
                String role = req.getParameter("role");

                List<User> users = adminBO.getAllUsers();
                req.setAttribute("users", users);
                req.getRequestDispatcher("/WebContent/Admin/UserManage.jsp").forward(req, resp);
            } else {
                try {
                    int id = Integer.parseInt(pathInfo.substring(1));
                    User user = userBO.getUserById(id);

                    if (user != null) {
                        req.setAttribute("user", user);
                        req.getRequestDispatcher("/WebContent/user-details.jsp").forward(req, resp);
                    } else {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
                    }
                } catch (NumberFormatException e) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("Invalid user ID");
                }
            }
        } else {
            // User: Chỉ được xem thông tin của bản thân
            if (pathInfo == null || pathInfo.equals("/") ||
                    !pathInfo.substring(1).equals(String.valueOf(loggedInUser.getId()))) {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                resp.getWriter().write("You are only allowed to view your own information");
            } else {
                req.setAttribute("user", loggedInUser);
                req.getRequestDispatcher("/WebContent/user-details.jsp").forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User loggedInUser = (User) req.getSession().getAttribute("user");

        if (loggedInUser != null && "admin".equals(loggedInUser.getRole())) {
            // Admin thêm người dùng
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String role = req.getParameter("role");
            String fullName = req.getParameter("fullName");
            String email = req.getParameter("email");
            String phone = req.getParameter("phone");
            String address = req.getParameter("address");

            User user = new User(username, password, role, fullName, email, phone, address);
            boolean success = adminBO.addUser(user);

            if (success) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("User created successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("Failed to create user");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().write("Unauthorized access");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User loggedInUser = (User) req.getSession().getAttribute("user");

        if (loggedInUser != null && "admin".equals(loggedInUser.getRole())) {
            // Admin sửa thông tin người dùng
            String pathInfo = req.getPathInfo();

            if (pathInfo == null || pathInfo.equals("/")) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("User ID is missing");
                return;
            }
            try {
                int id = Integer.parseInt(pathInfo.substring(1));

                String username = req.getParameter("username");
                String password = req.getParameter("password");
                String role = req.getParameter("role");
                String fullName = req.getParameter("fullName");
                String email = req.getParameter("email");
                String phone = req.getParameter("phone");
                String address = req.getParameter("address");

                User user = new User(id, username, password, role, fullName, email, phone, address);
                boolean success = adminBO.updateUser(user);

                if (success) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getWriter().write("User updated successfully");
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("User not found");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Invalid user ID");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().write("Unauthorized access");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User loggedInUser = (User) req.getSession().getAttribute("user");

        if (loggedInUser != null && "admin".equals(loggedInUser.getRole())) {
            // Admin xóa người dùng
            String pathInfo = req.getPathInfo();

            if (pathInfo == null || pathInfo.equals("/")) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("User ID is missing");
                return;
            }
            try {
                int id = Integer.parseInt(pathInfo.substring(1));
                boolean success = adminBO.deleteUser(id);

                if (success) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getWriter().write("User deleted successfully");
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("User not found");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Invalid user ID");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().write("Unauthorized access");
        }
    }
}
