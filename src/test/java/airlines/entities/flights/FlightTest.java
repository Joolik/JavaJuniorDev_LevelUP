package airlines.entities.flights;

import airlines.entities.aircrafts.PassengerPlane;
import airlines.entities.employees.Employee;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class FlightTest {

    private EntityManagerFactory factory;
    private EntityManager manager;

    @Before
    public void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory("TestPersistenceUnit");
        manager = factory.createEntityManager();
    }

    @After
    public void tearDown() throws Exception {
        if (manager != null) {
            manager.close();
        }
        if (factory != null) {
            factory.close();
        }
    }

    @Test
    public void entityFlight() throws ParseException {
        FlightsScheduleRecord scheduleRecord1, scheduleRecord2;
        scheduleRecord1 = new FlightsScheduleRecord().setFlightName("Y7 869")
                .setDepartureAirport("KJA")
                .setArriveAirport("BJS")
                .setPlaneType("Boeing 737-800")
                .setDepartureTime(LocalTime.of(20, 30))
                .setArriveTime(LocalTime.of(1, 20))
                .setDays("ср сб");
        scheduleRecord2 = new FlightsScheduleRecord().setFlightName("Y5 02")
                .setDepartureAirport("LED")
                .setArriveAirport("VVO")
                .setPlaneType("Airbus A330-200/300")
                .setDepartureTime(LocalTime.of(22, 50))
                .setArriveTime(LocalTime.of(9, 05))
                .setDays("чт");

        PassengerPlane plane = new PassengerPlane("PL5078", "Boeing 737-800", 180);

        Employee pilot1 = new Employee(118, "Михайлов Александр Алексеевич", "командир воздушного судна");
        Employee pilot2 = new Employee(95, "Тулин Виктор Петрович", "второй пилот");
        Employee airHostess = new Employee(156, "Свиридова Елена Дмитриевна", "старший бортпроводник");

        List<Employee> crew1 = new ArrayList<>();
        crew1.add(pilot1);
        crew1.add(pilot2);
        crew1.add(airHostess);

        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date date1 = format.parse("2019-11-08");
        Date date2 = format.parse("2019-12-30");
        Flight flight1 = new Flight(scheduleRecord1, date1, 3, plane, crew1);
        Flight flight2 = new Flight(scheduleRecord1, date2, 1, null, null);
        Flight flight3 = new Flight(scheduleRecord2, date2, 2, plane, crew1);

        manager.getTransaction().begin();
        try {
            manager.persist(scheduleRecord1);
            manager.persist(scheduleRecord2);

            manager.persist(plane);

            manager.persist(pilot1);
            manager.persist(pilot2);
            manager.persist(airHostess);

            manager.persist(flight1);
            manager.persist(flight2);
            manager.persist(flight3);

            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }

        List<Flight> foundFlights1 = manager.createQuery("from Flight where date = :p", Flight.class)
                .setParameter("p", date1)
                .getResultList();

        List<Flight> foundFlights2 = manager.createQuery("from Flight where date = :p", Flight.class)
                .setParameter("p", date2)
                .getResultList();

        Assert.assertEquals(flight1.getId(), foundFlights1.get(0).getId());
        Assert.assertEquals(2, foundFlights2.size());
    }

}