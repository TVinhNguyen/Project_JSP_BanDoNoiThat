package Controller.Admin;

import com.google.gson.Gson;
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
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.List;


@WebServlet("/admin/ProductManage/*")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class Products extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private GuestBO guestBO = new GuestBO();
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

            if (pathInfo == null ) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Action is missing");
                return;
            }

            String action = pathInfo.substring(1).toLowerCase(); // Action from pathInfo
            try {
                switch (action) {
                    case "":
                        viewProduct(req, resp);
                        break;
                    case "create":
                        createProduct(req, resp);
                        break;
                    case "update":
                        updateProduct(req, resp);
                        break;

                    case "delete":
                        deleteProduct(req, resp);
                        break;
                    case "edit":
                        viewEdit(req,resp);
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

        private void createProduct(HttpServletRequest req, HttpServletResponse resp) throws Exception {
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            double price = Double.parseDouble(req.getParameter("price"));
            int stock = Integer.parseInt(req.getParameter("stock"));
            int idCategory = Integer.parseInt(req.getParameter("idcategory"));

            Part imagePart = req.getPart("image");
            String originalImageName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
            String newImageName = System.currentTimeMillis() + ".webp";

            String uploadPath = "D:/Study/Servlet/Project_JSP_BanDoNoiThat/src/main/webapp/Image";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String filePath = uploadPath + File.separator + newImageName;

            imagePart.write(filePath);

            String databaseImagePath = "/Image/" + newImageName;


            Product product = new Product(name, description, price, stock, idCategory, databaseImagePath);
            boolean success = adminBO.addProduct(product);
            if (success) {
                resp.sendRedirect("/admin/ProductManage/");
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("Failed to create product");
            }
        }

    private void updateProduct(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int id = Integer.parseInt(req.getParameter("productId"));
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        double price = Double.parseDouble(req.getParameter("price"));
        int stock = Integer.parseInt(req.getParameter("stock"));
        int idCategory = Integer.parseInt(req.getParameter("idcategory"));

        Part imagePart = req.getPart("image");
        String databaseImagePath = null;

        if (imagePart != null && imagePart.getSize() > 0) {
            String originalImageName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
            String newImageName = System.currentTimeMillis() + ".webp";

            String uploadPath = "D:/Study/Servlet/Project_JSP_BanDoNoiThat/src/main/webapp/Image";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String filePath = uploadPath + File.separator + newImageName;
            imagePart.write(filePath);

            databaseImagePath = "/Image/" + newImageName;
        } else {
            databaseImagePath = req.getParameter("currentImage");
        }

        Product product = new Product(id, name, description, price, stock, idCategory, databaseImagePath);
        boolean success = adminBO.updateProduct(product);

        if (success) {
            resp.sendRedirect("/admin/ProductManage/");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Product not found");
        }
    }

    private  void viewEdit(HttpServletRequest req, HttpServletResponse resp) throws Exception {
            int id = Integer.parseInt(req.getParameter("id"));
            Product product = adminBO.getProductById(id);


            resp.setContentType("application/json");
            PrintWriter out = resp.getWriter();
            out.print(new Gson().toJson(product));
            out.flush();
        }
        private void deleteProduct(HttpServletRequest req, HttpServletResponse resp) throws Exception {
            int id = Integer.parseInt(req.getParameter("id"));
            boolean success = adminBO.deleteProduct(id);

            if (success) {
                resp.sendRedirect("/admin/ProductManage/");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("Product not found");
            }
        }
    private void viewProduct(HttpServletRequest req, HttpServletResponse resp) throws Exception {
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
        if(category != null) req.setAttribute("idcategory", category);
        req.getRequestDispatcher("/WebContent/Admin/ProductManage.jsp").forward(req, resp);
    }

}


