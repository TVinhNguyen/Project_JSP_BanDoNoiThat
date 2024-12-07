package Controller;

import model.bean.Product;
import model.bean.ProductView;
import model.bean.User;
import model.bo.AdminBO;
import model.bo.GuestBO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/Products/*")
public class Products extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private GuestBO guestBO = new GuestBO();
    private AdminBO adminBO = new AdminBO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            String name = req.getParameter("name");
            String category = req.getParameter("category");
            String minPriceParam = req.getParameter("minPrice");
            String maxPriceParam = req.getParameter("maxPrice");

            Double minPrice = null;
            Double maxPrice = null;
            int idCategory = 0;
            if (minPriceParam != null) {
                try {
                    minPrice = Double.parseDouble(minPriceParam);
                } catch (NumberFormatException e) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("Invalid minPrice");
                    return;
                }
            }

            if (maxPriceParam != null) {
                try {
                    maxPrice = Double.parseDouble(maxPriceParam);
                } catch (NumberFormatException e) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("Invalid maxPrice");
                    return;
                }
            }
            if(category != null) {
                try{
                    idCategory = Integer.parseInt(category);
                } catch (NumberFormatException e) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("Invalid category");
                    return;
                }
            }
            List<ProductView> products = guestBO.getProducts(name,idCategory, minPrice, maxPrice);
            req.setAttribute("products", products);
            req.getRequestDispatcher("/WebContent/product-list.jsp").forward(req, resp);
        } else {
            try {
                int id = Integer.parseInt(pathInfo.substring(1));
                Product product = guestBO.getProductById(id);
                if (product != null) {
                    req.setAttribute("product", product);
                    req.getRequestDispatcher("/product-detail.jsp").forward(req, resp);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Invalid product ID");
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");

        if (user != null && "admin".equals(user.getRole())) {
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            double price = Double.parseDouble(req.getParameter("price"));
            int stock = Integer.parseInt(req.getParameter("stock"));
            int idCategory = Integer.parseInt(req.getParameter("idcategory"));
            String image = req.getParameter("image");

            Product product = new Product(name, description, price, stock, idCategory, image);
            boolean success = adminBO.addProduct(product);

            if (success) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("Product created successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("Failed to create product");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().write("Unauthorized access");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");

        if (user != null && "admin".equals(user.getRole())) {
            String pathInfo = req.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Product ID is missing");
                return;
            }
            try {
                int id = Integer.parseInt(pathInfo.substring(1));

                String name = req.getParameter("name");
                String description = req.getParameter("description");
                double price = Double.parseDouble(req.getParameter("price"));
                int stock = Integer.parseInt(req.getParameter("stock"));
                int idCategory = Integer.parseInt(req.getParameter("idcategory"));
                String image = req.getParameter("image");

                Product product = new Product(id, name, description, price, stock, idCategory, image);
                boolean success = adminBO.updateProduct(product);

                if (success) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getWriter().write("Product updated successfully");
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("Product not found");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Invalid product ID");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().write("Unauthorized access");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");

        if (user != null && "admin".equals(user.getRole())) {
            String pathInfo = req.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Product ID is missing");
                return;
            }
            try {
                int id = Integer.parseInt(pathInfo.substring(1));
                boolean success = adminBO.deleteProduct(id);

                if (success) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getWriter().write("Product deleted successfully");
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("Product not found");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Invalid product ID");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().write("Unauthorized access");
        }
    }
}
