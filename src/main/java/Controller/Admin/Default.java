package Controller.Admin;

import com.google.gson.Gson;
import model.bo.AdminBO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/admin/default/*")
public class Default extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        AdminBO adminBO = new AdminBO();
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        if (pathInfo == null || pathInfo.equals("/")) {
            Gson gson = new Gson();

            int month = LocalDate.now().getMonthValue();
            int year = LocalDate.now().getYear();

            List<String> orderDates = new ArrayList<>();
            List<Integer> orderCounts = new ArrayList<>();
            Map<LocalDate, Integer> orders = adminBO.getOrdersInMonth(month, year);

            for (Map.Entry<LocalDate, Integer> entry : orders.entrySet()) {
                orderDates.add(entry.getKey().toString());
                orderCounts.add(entry.getValue());
            }

            String orderDatesJson = gson.toJson(orderDates);
            String orderCountsJson = gson.toJson(orderCounts);
            int totalProducs =  adminBO.getAllProducts().size();
            int totalOrders = adminBO.getAllOrders().size();
            req.setAttribute("totalProducts" , totalProducs);
            req.setAttribute("totalOrders" , totalOrders);

            req.setAttribute("orderDatesJson", orderDatesJson);
            req.setAttribute("orderCountsJson", orderCountsJson);
            req.setAttribute("month", month);
            req.setAttribute("year", year);

            req.getRequestDispatcher("/WebContent/Admin/default.jsp").forward(req, resp);
        }
    }
}