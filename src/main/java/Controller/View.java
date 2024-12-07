package Controller;

import model.bean.Product;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet({"/viewLogin","/viewProduct"})
public class View extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getRequestURI();
        if(path.equals("/viewLogin")){
            HttpSession session = req.getSession(false);
            if (session != null) {
                session.setAttribute("user", null);
            }

            String destination = "/WebContent/login.jsp";
            RequestDispatcher dispatcher = req.getRequestDispatcher(destination);
            dispatcher.forward(req, resp);
        }
        else if(path.equals("/viewProducts")){

            HttpSession session = req.getSession(false);
            if (session.getAttribute("user") != null ) {
                String destination = "/WebContent/Products_list.jsp";
                RequestDispatcher dispatcher = req.getRequestDispatcher(destination);
                dispatcher.forward(req, resp);
            }
        }

    }
}
