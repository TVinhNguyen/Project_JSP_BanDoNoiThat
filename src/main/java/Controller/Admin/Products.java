package Controller.Admin;

import model.bean.Categories;
import model.bean.Product;
import model.dto.ProductView;
import model.bean.User;
import model.bo.AdminBO;
import model.bo.GuestBO;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@WebServlet("/admin/ProductManage/*")
public class Products extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private GuestBO guestBO = new GuestBO();
    private AdminBO adminBO = new AdminBO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        System.out.println(pathInfo);

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
            List<Categories> categories = adminBO.getAllCategories();
            req.setAttribute("products", products);
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/WebContent/Admin/ProductManage.jsp").forward(req, resp);
//        } else {
//            try {
//                int id = Integer.parseInt(pathInfo.substring(1));
//                Product product = guestBO.getProductById(id);
//                if (product != null) {
//                    req.setAttribute("product", product);
//                    req.getRequestDispatcher("/product-detail.jsp").forward(req, resp);
//                } else {
//                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
//                }
//            } catch (NumberFormatException e) {
//                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                resp.getWriter().write("Invalid product ID");
//            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        User user = (User) req.getSession().getAttribute("user");
        System.out.println(req.getParameter("name"));
        if (user != null && "admin".equals(user.getRole())) {
            try {
                String name = req.getParameter("name");
                String description = req.getParameter("description");
                double price = Double.parseDouble(req.getParameter("price"));
                int stock = Integer.parseInt(req.getParameter("stock"));
                int idCategory = Integer.parseInt(req.getParameter("idcategory"));

                Part imagePart = req.getPart("image");
                String originalImageName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
                String newImageName = System.currentTimeMillis() + ".webp";

                String uploadPath = getServletContext().getRealPath("") + File.separator + "Image";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String filePath = uploadPath + File.separator + newImageName;
                imagePart.write(filePath);

                // Set the database image path (relative path)
                String databaseImagePath = "../Image/" + newImageName;

                // Create and save product
                Product product = new Product(name, description, price, stock, idCategory, databaseImagePath);
                boolean success = adminBO.addProduct(product);

                if (success) {
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    resp.getWriter().write("Product created successfully");
                } else {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    resp.getWriter().write("Failed to create product");
                }
            } catch (Exception e) {
                e.printStackTrace();
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Error processing request: " + e.getMessage());
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
