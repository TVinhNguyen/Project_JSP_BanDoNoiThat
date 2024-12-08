package Controller;
import com.google.gson.Gson;
import model.bean.Product;
import model.bo.AdminBO;
import model.dao.OrderDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
@WebServlet("/productDetails")
public class ProductDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AdminBO adminBO = new AdminBO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("id");

        if (productId != null) {

            Product product = adminBO.getProductById(Integer.parseInt(productId));

            if (product != null) {
                response.setContentType("application/json");
                response.getWriter().write(new Gson().toJson(product)); // Trả về dữ liệu sản phẩm dưới dạng JSON
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID is required");
        }
    }
}