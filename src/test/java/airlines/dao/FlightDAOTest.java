package airlines.dao;

import airlines.BaseTest;
import airlines.dao.aircrafts.PassengerPlaneDAOImpl;
import airlines.dao.aircrafts.PlaneTypeDAOImpl;
import airlines.dao.employees.EmployeeDAOImpl;
import airlines.dao.employees.UserDAOImpl;
import airlines.dao.flights.FlightDAOImpl;
import airlines.dao.flights.FlightsScheduleDAOImpl;
import airlines.entities.aircrafts.PassengerPlane;
import airlines.entities.aircrafts.PlaneType;
import airlines.entities.employees.Employee;
import airlines.entities.flights.Flight;
import airlines.entities.flights.FlightsScheduleRecord;
import airlines.entities.flights.enums.statuses.FlightStatuses;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static airlines.entities.employees.enums.EmployeePositions.*;
import static airlines.entities.flights.enums.AirportCodes.*;
import static airlines.entities.flights.enums.statuses.FlightStatuses.*;
import static org.junit.Assert.*;

public class FlightDAOTest extends BaseTest {

    private FlightDAO dao;

    private PlaneType planeType;
    private PassengerPlane plane;
    private FlightsScheduleRecord scheduleRecord;
    private Employee employee1, employee2;
    private List<Employee> crew;
    private Date date;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        dao = new FlightDAOImpl(manager);

        // заполняем базу необходимыми данными
        planeType = new PlaneType("B737-800", "Boeing 737-800", 180);
        plane = new PassengerPlane("PL5078", planeType);
        scheduleRecord = new FlightsScheduleRecord()
                .setFlightName("Y7 869")
                .setDepartureAirport(KJA)
                .setArriveAirport(SVX)
                .setPlaneType(planeType)
                .setDepartureTime(LocalTime.of(20, 30))
                .setArriveTime(LocalTime.of(1, 20))
                .setDays("ср сб");
        employee1 = new Employee(118, "Михайлов Александр Алексеевич", CAPTAIN);
        employee2 = new Employee(156, "Свиридова Елена Дмитриевна", PURSER);
        crew = new ArrayList<>();
        crew.add(employee1);
        crew.add(employee2);
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        date = format.parse("2019-11-08");

        manager.getTransaction().begin();
        try {
            manager.persist(planeType);
            manager.persist(plane);
            manager.persist(scheduleRecord);
            manager.persist(employee1);
            manager.persist(employee2);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
    }

    @Test
    public void create() {
        Flight flight = new Flight(scheduleRecord, date, NEW, plane, crew);
        manager.getTransaction().begin();
        try {
            dao.create(flight);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        Flight found = manager.find(Flight.class, flight.getId());
        assertNotNull("Record not found", found);
    }

    @Test
    public void update() {
        Flight flight = new Flight(scheduleRecord, date, CHECK_IN, null, null);
        manager.getTransaction().begin();
        try {
            manager.persist(flight);
            flight.setPlane(plane).setCrewMembers(crew);
            dao.update(flight);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        Flight found = manager.createQuery("from Flight where id = :p", Flight.class)
                .setParameter("p", flight.getId())
                .getSingleResult();
        assertEquals("No update for plane", plane, found.getPlane());
        assertEquals("No update for crew", crew, found.getCrewMembers());
    }

    @Test
    public void delete() {
        Flight flight = new Flight(scheduleRecord, date, NEW, plane, crew);
        manager.getTransaction().begin();
        try {
            manager.persist(flight);
            dao.delete(flight);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        Flight found = manager.find(Flight.class, flight.getId());
        assertNull("Exist record in DB", found);
    }

    @Test
    public void findById() {
        Flight flight = new Flight(scheduleRecord, date, NEW, plane, crew);
        manager.getTransaction().begin();
        try {
            manager.persist(flight);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        Flight found = manager.createQuery("from Flight where id = :p", Flight.class)
                .setParameter("p", flight.getId())
                .getSingleResult();
        assertEquals("Wrong flight found by id", found, dao.findById(flight.getId()));
    }

    @Ignore
    @Test
    public void findByDateRange() {
    }

    @Test
    public void findAll() {
//        // заполняем таблицу данными
//
//        EmployeeDAO employeeDAO = new EmployeeDAOImpl(manager);
//        UserDAO userDAO = new UserDAOImpl(manager);
//        PlaneTypeDAO planeTypeDAO = new PlaneTypeDAOImpl(manager);
//        PassengerPlaneDAO planeDAO = new PassengerPlaneDAOImpl(manager);
//        FlightScheduleDAO flightScheduleDAO = new FlightsScheduleDAOImpl(manager);
//        FlightDAO flightDAO = new FlightDAOImpl(manager);
//
//        // создаем админа
//
//        Employee captain1 = new Employee(119, "Михайлов Александр Алексеевич", CAPTAIN);
//        Employee captain2 = new Employee(3, "Кныш Владислав Викторович", CAPTAIN);
//        Employee pilot1 = new Employee(17, "Дятлов Андрей Николаевич", FIRST_OFFICER);
//        Employee pilot2 = new Employee(7, "Рябинин Роман Александрович", FIRST_OFFICER);
//        Employee purser1 = new Employee(122, "Свиридова Елена Дмитриевна", PURSER);
//        Employee airhostess1 = new Employee(19, "Плотникова Вера Александровна", FLIGHT_ATTENDANT);
//        Employee airhostess2 = new Employee(20, "Пряникова Евгения Михайловна", FLIGHT_ATTENDANT);
//
//        List<Employee> crew = new ArrayList<>();
//        crew.add(captain1);
//        crew.add(airhostess1);
//
//        PlaneType planeType2 = new PlaneType("SSJ-100", "Sukhoi Superjet 100-95B", 87);
//        PlaneType planeType3 = new PlaneType("B777-300ER", "Boeing 777-300ER", 402);
//        PassengerPlane plane1 = new PassengerPlane("VP-BIY", planeType);
//        PassengerPlane plane2 = new PassengerPlane("RA-89053", planeType2);
//        PassengerPlane plane3 = new PassengerPlane("RA-89102", planeType2);
//        PassengerPlane plane4 = new PassengerPlane("VP-BGT", planeType3);
//
//        FlightsScheduleRecord scheduleRecord2 = new FlightsScheduleRecord().setFlightName("Y5 02")
//                .setDepartureAirport(LED)
//                .setArriveAirport(VVO)
//                .setPlaneType(planeType)
//                .setDepartureTime(LocalTime.of(22,50))
//                .setArriveTime(LocalTime.of(9, 05))
//                .setDays("чт");
//
//        SimpleDateFormat format = new SimpleDateFormat();
//        format.applyPattern("yyyy-MM-dd");
//        Date date1 = null, date2 = null;
//        try {
//            date1 = format.parse("2019-11-08");
//            date2 = format.parse("2019-12-30");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Flight flight1 = new Flight(scheduleRecord, date1, COMPLETED, plane1, crew);
//        Flight flight2 = new Flight(scheduleRecord, date2, CANCELED, null, null);
//        Flight flight3 = new Flight(scheduleRecord2, date2, NEW, null, null);
//        manager.getTransaction().begin();
//        try {
//            employeeDAO.create(captain1);
//            employeeDAO.create(captain2);
//            employeeDAO.create(pilot1);
//            employeeDAO.create(pilot2);
//            employeeDAO.create(purser1);
//            employeeDAO.create(airhostess1);
//            employeeDAO.create(airhostess2);
//            planeTypeDAO.create(planeType2);
//            planeTypeDAO.create(planeType3);
//            planeDAO.create(plane1);
//            planeDAO.create(plane2);
//            planeDAO.create(plane3);
//            planeDAO.create(plane4);
//            flightScheduleDAO.create(scheduleRecord2);
//            flightDAO.create(flight1);
//            flightDAO.create(flight2);
//            flightDAO.create(flight3);
//            manager.getTransaction().commit();
//        } catch (Exception e) {
//            manager.getTransaction().rollback();
//            throw e;
//        }
        Flight flight = new Flight(scheduleRecord, date, NEW, plane, crew);
        manager.getTransaction().begin();
        try {
            manager.persist(flight);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        List<Flight> found = manager.createQuery("from Flight", Flight.class)
                .getResultList();
        List<Flight> daoList = dao.findAll();
        daoList.forEach(flight4 -> System.out.println(flight4.toString()));
        assertEquals("Wrong size", found.size(), daoList.size());
        assertArrayEquals("Wrong flight list found", found.toArray(), daoList.toArray());
    }

    @Test
    public void getPlaneByFlightId() {
        Flight flight = new Flight(scheduleRecord, date, NEW, plane, crew);
        manager.getTransaction().begin();
        try {
            manager.persist(flight);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        Flight found = manager.createQuery("from Flight where id = :p", Flight.class)
                .setParameter("p", flight.getId())
                .getSingleResult();
        assertEquals("No plane", flight.getPlane(), dao.getPlaneByFlightId(flight.getId()));
    }

    @Test
    public void getCrewByFlightId() {
        Flight flight = new Flight(scheduleRecord, date, NEW, plane, crew);
        manager.getTransaction().begin();
        try {
            manager.persist(flight);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        Flight found = manager.createQuery("from Flight where id = :p", Flight.class)
                .setParameter("p", flight.getId())
                .getSingleResult();
        assertEquals("No crew", flight.getCrewMembers(), dao.getCrewByFlightId(flight.getId()));
    }

    @Test
    public void setPlane() {
        Flight flight = new Flight(scheduleRecord, date, CHECK_IN, null, null);
        manager.getTransaction().begin();
        try {
            manager.persist(flight);
            dao.setPlane(flight, plane);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        Flight found = manager.createQuery("from Flight where id = :p", Flight.class)
                .setParameter("p", flight.getId())
                .getSingleResult();
        assertEquals("No update for plane", plane, flight.getPlane());
    }

    @Test
    public void setCrew() {
        Flight flight = new Flight(scheduleRecord, date, CHECK_IN, null, null);
        manager.getTransaction().begin();
        try {
            manager.persist(flight);
            dao.setCrew(flight, crew);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        Flight found = manager.createQuery("from Flight where id = :p", Flight.class)
                .setParameter("p", flight.getId())
                .getSingleResult();
        assertEquals("No update for crew", crew, found.getCrewMembers());
    }

    @Test
    public void changeFlightStatus() {
        Flight flight = new Flight(scheduleRecord, date, CHECK_IN, plane, crew);
        FlightStatuses newStatus = BOARDING_COMPLETED;
        manager.getTransaction().begin();
        try {
            manager.persist(flight);
            dao.changeFlightStatus(flight, newStatus);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        Flight found = manager.createQuery("from Flight where id = :p", Flight.class)
                .setParameter("p", flight.getId())
                .getSingleResult();
        assertEquals("No update for status", newStatus, found.getStatusId());

    }
}