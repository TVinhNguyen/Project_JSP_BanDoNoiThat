//package Controller;
//
//import com.google.gson.Gson;
//import model.bean.Product;
//import model.bean.User;
//import model.bo.AdminBO;
//
//import javax.print.attribute.standard.Destination;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.util.List;
//
//@WebServlet("/admin/*")
//public class DashBoard extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//    private AdminBO adminBO = new AdminBO();
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String path = req.getPathInfo();
//        if(path == null || path.equals("/")) {
//
//            req.getRequestDispatcher("/WebContent/Admin/dashboard.jsp").forward(req, resp);
//
//        }
//        else  if (path.equals("/ProductManage")) {
//            handlerProductManage(req,resp);
//
//        } else if (path.equals("/UserManage")) {
//            List<User> users = adminBO.getAllUsers();
//            resp.setContentType("application/json");
//            resp.getWriter().write(new Gson().toJson(users));
//        } else {
//            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found");
//        }
//
//    }
//    private void handlerProductManage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        List<Product> products = adminBO.getAllProducts();
//        resp.setContentType("application/json");
//        resp.getWriter().write(new Gson().toJson(products));
//    }
//}
