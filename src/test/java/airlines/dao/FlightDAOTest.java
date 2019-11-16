package airlines.dao;

import airlines.BaseTest;
import airlines.dao.flights.FlightDAOImpl;
import airlines.entities.aircrafts.PassengerPlane;
import airlines.entities.aircrafts.PlaneType;
import airlines.entities.employees.Employee;
import airlines.entities.flights.Flight;
import airlines.entities.flights.FlightsScheduleRecord;
import airlines.entities.flights.enums.statuses.FlightStatusesEnum;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static airlines.entities.employees.enums.EmployeePositionsEnum.*;
import static airlines.entities.flights.enums.AirportCodesEnum.*;
import static airlines.entities.flights.enums.statuses.FlightStatusesEnum.*;
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
        crew.add(employee1);
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
        assertArrayEquals("Wrong flight list found", found.toArray(), dao.findAll().toArray());
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
        FlightStatusesEnum newStatus = BOARDING_COMPLETED;
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