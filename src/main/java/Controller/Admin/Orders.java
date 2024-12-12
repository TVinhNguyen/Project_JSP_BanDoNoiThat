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
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo();
        User loggedInUser = (User) req.getSession().getAttribute("user");

        if (loggedInUser == null || !"admin".equals(loggedInUser.getRole())) {
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
                    viewOrders(req, resp);
                    break;
//                case "update":
//                    updateOrder(req, resp);
//                    break;
//                case "delete":
//                    deleteOrder(req, resp);
//                    break;
//                case "edit":
//                    viewEdit(req, resp);
//                    break;
                case "orderdetails":
                    viewOrderDetails(req, resp);
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

    private void viewOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Order> orders = adminBO.getAllOrders();
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/WebContent/Admin/OrderManage.jsp").forward(req, resp);
    }

//    private void createOrder(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//        String userId = req.getParameter("userId");
//        String totalAmount = req.getParameter("totalAmount");
//        int id = 0; double total=0;
//        if(userId != null && totalAmount != null) {
//             id = Integer.parseInt(userId);
//             total = Double.parseDouble(totalAmount);
//        }
//        boolean success=true;
//        if(id!=0) {
//            Order order = new Order(id, total);
//            success = adminBO.addOrder(order);
//
//        }
//
//
//        if (success) {
//            resp.sendRedirect("/admin/OrderManage/");
//        } else {
//            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            resp.getWriter().write("Failed to create order");
//        }
//    }
//
//    private void updateOrder(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//        String orderId = req.getParameter("orderId");
//        String totalAmount = req.getParameter("totalAmount");
//
//        Order order = new Order(orderId, totalAmount);
//        boolean success = adminBO.updateOrder(order);
//
//        if (success) {
//            resp.sendRedirect("/admin/OrderManage/");
//        } else {
//            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            resp.getWriter().write("Order not found");
//        }
//    }
//
//    private void deleteOrder(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//        String orderId = req.getParameter("orderId");
//        boolean success = adminBO.deleteOrder(orderId);
//
//        if (success) {
//            resp.sendRedirect("/admin/OrderManage/");
//        } else {
//            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            resp.getWriter().write("Order not found");
//        }
//    }
//
//    private void viewEdit(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//        String orderId = req.getParameter("orderId");
//        Order order = adminBO.getOrder(Integer.parseInt(orderId));
//
//        resp.setContentType("application/json");
//        PrintWriter out = resp.getWriter();
//        out.print(new Gson().toJson(order));
//        out.flush();
//    }

    private void viewOrderDetails(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String orderId = req.getParameter("orderId");

        if (orderId != null && !orderId.isEmpty()) {
            List<OrderDetailsView> orderDetails = adminBO.getOrderDetailsByOrderId(orderId);
            Order order = adminBO.getOrder(Integer.parseInt(orderId));

            if (order != null) {
                req.setAttribute("order", order);
                req.setAttribute("orderDetails", orderDetails);
                req.getRequestDispatcher("/WebContent/Admin/orderDetails.jsp").forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Order ID is required");
        }
    }
}
