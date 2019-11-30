package airlines.web.servlets;

import airlines.dao.UserDAO;
import airlines.dao.employees.UserDAOImpl;
import airlines.entities.employees.User;
import airlines.web.StartupListener;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        EntityManagerFactory factory = StartupListener.getFactory(req.getServletContext());
        EntityManager manager = factory.createEntityManager();
        UserDAO userDAO = new UserDAOImpl(manager);
        try {
            User user = userDAO.findByLoginAndPassword(login, password);
            if (user == null) {       // ошибка "неверный логин/пароль"
                resp.getWriter().print("Wrong login/password!");
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
            } else if (user.getAdmin()) {
                resp.sendRedirect("/views/flights.jsp");
            } else {
                resp.sendRedirect("/views/schedule.jsp");
            }
        } catch (Exception e) {
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        } finally {
            manager.close();
        }
    }
}
