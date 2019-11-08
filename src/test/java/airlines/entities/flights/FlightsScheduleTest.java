package airlines.entities.flights;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.time.LocalTime;

import static org.junit.Assert.*;

public class FlightsScheduleTest {

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
    public void entitySchedule() {
        FlightsScheduleRecord scheduleRecord1, scheduleRecord2;
        scheduleRecord1 = new FlightsScheduleRecord().setFlightName("Y7 869")
                .setDepartureAirport("KJA")
                .setArriveAirport("BJS")
                .setPlaneType("Boeing 737-800")
                .setDepartureTime(LocalTime.of(20,30))
                .setArriveTime(LocalTime.of(1,20))
                .setDays("ср сб");
        scheduleRecord2 = new FlightsScheduleRecord().setFlightName("Y5 02")
                .setDepartureAirport("LED")
                .setArriveAirport("VVO")
                .setPlaneType("Airbus A330-200/300")
                .setDepartureTime(LocalTime.of(22,50))
                .setArriveTime(LocalTime.of(9, 05))
                .setDays("чт");

        manager.getTransaction().begin();
        try {
            manager.persist(scheduleRecord1);
            manager.persist(scheduleRecord2);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        FlightsScheduleRecord flightsSchedule = manager.find(FlightsScheduleRecord.class, "Y7 869");
        System.out.println("OUTPUT " + flightsSchedule.toString());
        assertNotNull("Record not found", flightsSchedule);

    }
}