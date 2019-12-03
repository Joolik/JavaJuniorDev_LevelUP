package airlines.web;

import airlines.dao.*;
import airlines.dao.aircrafts.PassengerPlaneDAOImpl;
import airlines.dao.aircrafts.PlaneTypeDAOImpl;
import airlines.dao.employees.EmployeeDAOImpl;
import airlines.dao.employees.UserDAOImpl;
import airlines.dao.flights.FlightDAOImpl;
import airlines.dao.flights.FlightsScheduleDAOImpl;
import airlines.entities.aircrafts.PassengerPlane;
import airlines.entities.aircrafts.PlaneType;
import airlines.entities.employees.Employee;
import airlines.entities.employees.User;
import airlines.entities.flights.Flight;
import airlines.entities.flights.FlightsScheduleRecord;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static airlines.entities.employees.enums.EmployeePositions.*;
import static airlines.entities.flights.enums.AirportCodes.*;
import static airlines.entities.flights.enums.AirportCodes.VVO;
import static airlines.entities.flights.enums.statuses.FlightStatuses.*;

@WebListener
public class StartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("TestPersistenceUnit");
        EntityManager manager = factory.createEntityManager();

        // заполняем таблицу данными

        EmployeeDAO employeeDAO = new EmployeeDAOImpl(manager);
        UserDAO userDAO = new UserDAOImpl(manager);
        PlaneTypeDAO planeTypeDAO = new PlaneTypeDAOImpl(manager);
        PassengerPlaneDAO planeDAO = new PassengerPlaneDAOImpl(manager);
        FlightScheduleDAO flightScheduleDAO = new FlightsScheduleDAOImpl(manager);
        FlightDAO flightDAO = new FlightDAOImpl(manager);

        // создаем админа
        Employee manager1 = new Employee(95, "Тулин Виктор Петрович", MANAGER);
        User user1 = new User().setLogin("admin").setPassword("123").setEmployee(manager1).setAdmin(true);

        Employee captain1 = new Employee(118, "Михайлов Александр Алексеевич", CAPTAIN);
        Employee captain2 = new Employee(3, "Кныш Владислав Викторович", CAPTAIN);
        Employee pilot1 = new Employee(17, "Дятлов Андрей Николаевич", FIRST_OFFICER);
        Employee pilot2 = new Employee(7, "Рябинин Роман Александрович", FIRST_OFFICER);
        Employee purser1 = new Employee(156, "Свиридова Елена Дмитриевна", PURSER);
        Employee airhostess1 = new Employee(19, "Плотникова Вера Александровна", FLIGHT_ATTENDANT);
        Employee airhostess2 = new Employee(20, "Пряникова Евгения Михайловна", FLIGHT_ATTENDANT);

        User user2 = new User().setLogin("test1").setPassword("123").setEmployee(captain1).setAdmin(false);
        User user3 = new User().setLogin("test2").setPassword("123").setEmployee(purser1).setAdmin(false);

        List<Employee> crew = new ArrayList<>();
        crew.add(captain1);
        crew.add(airhostess1);

        PlaneType planeType1 = new PlaneType("B737-800", "Boeing 737-800", 180);
        PlaneType planeType2 = new PlaneType("SSJ-100", "Sukhoi Superjet 100-95B", 87);
        PlaneType planeType3 = new PlaneType("B777-300ER", "Boeing 777-300ER", 402);
        PassengerPlane plane1 = new PassengerPlane("VP-BIY", planeType1);
        PassengerPlane plane2 = new PassengerPlane("RA-89053", planeType2);
        PassengerPlane plane3 = new PassengerPlane("RA-89102", planeType2);
        PassengerPlane plane4 = new PassengerPlane("VP-BGT", planeType3);

        FlightsScheduleRecord scheduleRecord1 = new FlightsScheduleRecord().setFlightName("Y7 869")
                .setDepartureAirport(KJA)
                .setArriveAirport(SVX)
                .setPlaneType(planeType1)
                .setDepartureTime(LocalTime.of(20,30))
                .setArriveTime(LocalTime.of(1,20))
                .setDays("ср сб");
        FlightsScheduleRecord scheduleRecord2 = new FlightsScheduleRecord().setFlightName("Y5 02")
                .setDepartureAirport(LED)
                .setArriveAirport(VVO)
                .setPlaneType(planeType1)
                .setDepartureTime(LocalTime.of(22,50))
                .setArriveTime(LocalTime.of(9, 05))
                .setDays("чт");

        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date date1 = null, date2 = null;
        try {
            date1 = format.parse("2019-11-08");
            date2 = format.parse(String.valueOf(Instant.now().plus(10, ChronoUnit.DAYS)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Flight flight1 = new Flight(scheduleRecord1, date1, COMPLETED, plane1, crew);
        Flight flight2 = new Flight(scheduleRecord1, date2, NEW, null, null);
        Flight flight3 = new Flight(scheduleRecord2, date2, NEW, null, null);
        manager.getTransaction().begin();
        try {
            employeeDAO.create(manager1);
            employeeDAO.create(captain1);
            employeeDAO.create(captain2);
            employeeDAO.create(pilot1);
            employeeDAO.create(pilot2);
            employeeDAO.create(purser1);
            employeeDAO.create(airhostess1);
            employeeDAO.create(airhostess2);
            userDAO.create(user1);
            userDAO.create(user2);
            userDAO.create(user3);
            planeTypeDAO.create(planeType1);
            planeTypeDAO.create(planeType2);
            planeTypeDAO.create(planeType3);
            planeDAO.create(plane1);
            planeDAO.create(plane2);
            planeDAO.create(plane3);
            planeDAO.create(plane4);
            flightScheduleDAO.create(scheduleRecord1);
            flightScheduleDAO.create(scheduleRecord2);
            flightDAO.create(flight1);
            flightDAO.create(flight2);
            flightDAO.create(flight3);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        } finally {
            manager.close();
        }

        event.getServletContext().setAttribute("factory", factory);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        EntityManagerFactory factory = getFactory(event.getServletContext());
        if (factory != null) {
            factory.close();
        }
    }

    public static EntityManagerFactory getFactory(ServletContext context) {
        return (EntityManagerFactory) context.getAttribute("factory");
    }
}
