package Controller;

import model.bean.OrderDetail;
import model.bo.AdminBO;
import model.dao.OrderDAO;
import model.dto.OrderDetailsView;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/orderDetails")
public class OrderDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    AdminBO adminBO = new AdminBO();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        String orderId = request.getParameter("orderId");

        List<OrderDetailsView> orderDetails = adminBO.getOrderDetailsByOrderId(orderId);

        request.setAttribute("orderDetails", orderDetails);
        request.getRequestDispatcher("/WebContent/Admin/orderDetails.jsp").forward(request, response);
    }
}
