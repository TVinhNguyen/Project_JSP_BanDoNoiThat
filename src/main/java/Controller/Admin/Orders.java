package Controller.Admin;

import model.bean.Order;
import model.bean.OrderDetail;
import model.bean.User;
import model.bo.AdminBO;
import model.bo.UserBO;
import model.dto.OrderDetailsView;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/OrderManage/*")
public class Orders extends HttpServlet {
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
                List<Order> orders = adminBO.getAllOrders();
                req.setAttribute("orders", orders);
                req.getRequestDispatcher("/WebContent/Admin/OrderManage.jsp").forward(req, resp);
            } else if (pathInfo.equals("/orderDetails")) {
                String orderId = req.getParameter("orderId");

                if (orderId != null && !orderId.isEmpty()) {
                    try {
                        List<OrderDetailsView> orderDetails = adminBO.getOrderDetailsByOrderId(orderId);
                        Order order = adminBO.getOrder(Integer.parseInt(orderId));

                        if (order != null) {
                            req.setAttribute("order", order);
                            req.setAttribute("orderDetails", orderDetails);
                            req.getRequestDispatcher("/WebContent/Admin/orderDetails.jsp").forward(req, resp);
                        } else {
                            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found");
                        }
                    } catch (Exception e) {
                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        resp.getWriter().write("Invalid order ID");
                    }
                } else {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Order ID is required");
                }
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid path");
            }
        } else {
            if (pathInfo == null || pathInfo.equals("/") ||
                    !pathInfo.substring(1).equals(String.valueOf(loggedInUser.getId()))) {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                resp.getWriter().write("You are only allowed to view your own orders");
            } else {
                List<Order> orders = userBO.getOrdersByUserId(loggedInUser.getId());
                req.setAttribute("orders", orders);
                req.getRequestDispatcher("/WebContent/order-list.jsp").forward(req, resp);
            }
        }
    }



//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        User loggedInUser = (User) req.getSession().getAttribute("user");
//
//        if (loggedInUser != null && "admin".equals(loggedInUser.getRole())) {
//            // Admin thêm đơn hàng mới
//            String userId = req.getParameter("userId");
//            String totalAmount = req.getParameter("totalAmount");
//
//            Order order = new Order(userId, totalAmount);
//            boolean success = adminBO.addOrder(order);
//
//            if (success) {
//                resp.setStatus(HttpServletResponse.SC_CREATED);
//                resp.getWriter().write("Order created successfully");
//            } else {
//                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//                resp.getWriter().write("Failed to create order");
//            }
//        } else {
//            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            resp.getWriter().write("Unauthorized access");
//        }
//    }
//
//    @Override
//    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        User loggedInUser = (User) req.getSession().getAttribute("user");
//
//        if (loggedInUser != null && "admin".equals(loggedInUser.getRole())) {
//            // Admin cập nhật thông tin đơn hàng
//            String pathInfo = req.getPathInfo();
//
//            if (pathInfo == null || pathInfo.equals("/")) {
//                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                resp.getWriter().write("Order ID is missing");
//                return;
//            }
//            try {
//                String orderId = pathInfo.substring(1);
//
//                String totalAmount = req.getParameter("totalAmount");
//                Order order = new Order(orderId, totalAmount);
//                boolean success = adminBO.updateOrder(order);
//
//                if (success) {
//                    resp.setStatus(HttpServletResponse.SC_OK);
//                    resp.getWriter().write("Order updated successfully");
//                } else {
//                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
//                    resp.getWriter().write("Order not found");
//                }
//            } catch (Exception e) {
//                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                resp.getWriter().write("Invalid order ID");
//            }
//        } else {
//            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            resp.getWriter().write("Unauthorized access");
//        }
//}
//
//@Override
//protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//    User loggedInUser = (User) req.getSession().getAttribute("user");
//
//    if (loggedInUser != null && "admin".equals(loggedInUser.getRole())) {
//        // Admin xóa đơn hàng
//        String pathInfo = req.getPathInfo();
//
//        if (pathInfo == null || pathInfo.equals("/")) {
//            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            resp.getWriter().write("Order ID is missing");
//            return;
//        }
//        try {
//            String orderId = pathInfo.substring(1);
//            boolean success = adminBO.deleteOrder(orderId);
//
//            if (success) {
//                resp.setStatus(HttpServletResponse.SC_OK);
//                resp.getWriter().write("Order deleted successfully");
//            } else {
//                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
//                resp.getWriter().write("Order not found");
//            }
//        } catch (Exception e) {
//            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            resp.getWriter().write("Invalid order ID");
//        }
//    } else {
//        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        resp.getWriter().write("Unauthorized access");
//    }
//}
}
