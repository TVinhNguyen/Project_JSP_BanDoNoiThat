package Controller.Admin;

import com.google.gson.Gson;
import model.bean.User;
import model.bo.AdminBO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/admin/UserManage/*")
public class Users extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AdminBO adminBO = new AdminBO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo();
        User user = (User) req.getSession().getAttribute("user");

        if (user == null || !"admin".equals(user.getRole())) {
            resp.sendRedirect("/");
            return;
        }

        if (pathInfo == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Action is missing");
            return;
        }

        String action = pathInfo.substring(1).toLowerCase(); // Action from pathInfo
        try {
            switch (action) {
                case "":
                    viewUsers(req, resp);
                    break;
                case "create":
                    createUser(req, resp);
                    break;
                case "update":
                    updateUser(req, resp);
                    break;
                case "delete":
                    deleteUser(req, resp);
                    break;
                case "edit":
                    viewEdit(req, resp);
                    break;
                default:
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("Invalid action");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error processing request: " + e.getMessage());
        }
    }

    private void createUser(HttpServletRequest req, HttpServletResponse resp) throws Exception {
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
            resp.sendRedirect("/admin/UserManage/");
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Failed to create user");
        }
    }

    private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int id = Integer.parseInt(req.getParameter("userId"));
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
            resp.sendRedirect("/admin/UserManage/");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("User not found");
        }
    }

    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int id = Integer.parseInt(req.getParameter("id"));
        boolean success = adminBO.deleteUser(id);

        if (success) {
            resp.sendRedirect("/admin/UserManage/");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("User not found");
        }
    }

    private void viewEdit(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int id = Integer.parseInt(req.getParameter("id"));
        User user = adminBO.getUserById(id);

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print(new Gson().toJson(user));
        out.flush();
    }

    private void viewUsers(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        List<User> users = adminBO.getAllUsers();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/WebContent/Admin/UserManage.jsp").forward(req, resp);
    }
}
