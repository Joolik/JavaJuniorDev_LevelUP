package airlines.web.servlets;

import airlines.dao.FlightDAO;
import airlines.dao.flights.FlightDAOImpl;
import airlines.entities.flights.Flight;
import airlines.web.StartupListener;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/flights")
public class FlightsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManagerFactory factory = StartupListener.getFactory(req.getServletContext());
        EntityManager manager = factory.createEntityManager();
        FlightDAO flightDAO = new FlightDAOImpl(manager);

        try {
            String login = (String) req.getSession().getAttribute("userLogin");
            List<Flight> flights = flightDAO.findAll();
            req.setAttribute("flights", flights);
            req.getRequestDispatcher("/views/flights.jsp").forward(req, resp);
        } catch (NoResultException notFound) {
            req.getRequestDispatcher("/").forward(req, resp);
        } finally {
            manager.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String flightId = req.getParameter("flightId");
        int id = Integer.parseInt(flightId);

//        EntityManagerFactory factory = StartupListener.getFactory(req.getServletContext());
//        EntityManager manager = factory.createEntityManager();
//        FlightDAO flightDAO = new FlightDAOImpl(manager);
//        try {
//            Flight flight = flightDAO.findById(id);
//            if (flight == null) {       // не найден рейс
//                req.getRequestDispatcher("/views/flights.jsp").forward(req, resp);
//            } else {
//                    resp.sendRedirect("/flights?id="+flightId);
//            }
//        } catch (Exception e) {
//            req.getRequestDispatcher("/views/flights.jsp").forward(req, resp);
//        } finally {
//            manager.close();
//        }
    }
}
