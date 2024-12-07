package Controller;

import model.bean.Product;
import model.bean.User;
import model.bo.AdminBO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/*")
public class DashBoard extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AdminBO adminBO = new AdminBO();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if(path != null || !path.equals("")) {
            switch (path) {
                case "dashboard":
                    viewDashboard(req, resp);
                    break;
                case "logout":
                    HttpSession session = req.getSession();
                    session.invalidate();
                    resp.sendRedirect("/viewLogin");
                    break;
                case "viewProduct":
                    showProduct(req, resp);
                    break;
                }

        }

    }
    private void viewDashboard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals("admin")) {
            resp.sendRedirect("/viewLogin");
            return;
        }

        req.getRequestDispatcher("WebContent/admin-dashboard.jsp").forward(req, resp);
    }
    private void showProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = adminBO.getAllProducts();
        req.setAttribute("products", products);
        req.getRequestDispatcher("/viewProduct").forward(req, resp);
    }
}
