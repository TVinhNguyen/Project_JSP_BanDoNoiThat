package Controller;

import model.bean.User;
import model.bo.GuestBO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet({"/login","/register"})
public class LoginAndRegister extends HttpServlet {
    private static final long serialVersionUID = 1L;
    GuestBO guestBO = new GuestBO();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getRequestURI();
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        if(path.equals("/login")){
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            User user = guestBO.login(username, password);
            if(user != null) {
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                if(user.getRole().equals("admin")) {
                    resp.sendRedirect(req.getContextPath() + "/admin");
                }
            }
            else {
                req.setAttribute("error", "Invalid username or password");
                req.getRequestDispatcher("/WebContent/login.jsp").forward(req, resp);
            }
        } else if(path.equals("/register")){
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String fullName = req.getParameter("fullName");
            String email = req.getParameter("email");
            String phone = req.getParameter("phone");
            String address = req.getParameter("address");
            boolean bool = guestBO.registerUser(new User(username,password,"user",fullName,email,phone,address));
            req.getRequestDispatcher("/WebContent/login.jsp").forward(req, resp);
        }


    }
}


