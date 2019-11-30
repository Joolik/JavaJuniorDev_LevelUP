package airlines.dao;

import airlines.BaseTest;
import airlines.dao.flights.FlightsScheduleDAOImpl;
import airlines.entities.aircrafts.PlaneType;
import airlines.entities.flights.FlightsScheduleRecord;
import airlines.entities.flights.enums.AirportCodes;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalTime;

import static airlines.entities.flights.enums.AirportCodes.*;
import static org.junit.Assert.*;

public class FlightScheduleDAOTest extends BaseTest {

    private FlightScheduleDAO dao;
    private PlaneType planeType;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        dao = new FlightsScheduleDAOImpl(manager);
        planeType = new PlaneType("B737-800", "Boeing 737-800", 180);
    }

    @Test
    public void create() {
        FlightsScheduleRecord scheduleRecord = new FlightsScheduleRecord()
                .setFlightName("Y7 869")
                .setDepartureAirport(KJA)
                .setArriveAirport(SVX)
                .setPlaneType(planeType)
                .setDepartureTime(LocalTime.of(20, 30))
                .setArriveTime(LocalTime.of(1, 20))
                .setDays("ср сб");
        manager.getTransaction().begin();
        try {
            manager.persist(planeType);
            dao.create(scheduleRecord);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        FlightsScheduleRecord found = manager.find(FlightsScheduleRecord.class, scheduleRecord.getFlightName());
        assertNotNull("Record not found", found);
    }

    @Test
    public void update() {
        FlightsScheduleRecord scheduleRecord = new FlightsScheduleRecord()
                .setFlightName("Y7 869")
                .setDepartureAirport(KJA)
                .setArriveAirport(SVX)
                .setPlaneType(planeType)
                .setDepartureTime(LocalTime.of(20, 30))
                .setArriveTime(LocalTime.of(1, 20))
                .setDays("ср сб");
        AirportCodes newDepartureAirport = PEK;
        LocalTime newDepartureTime = LocalTime.of(11, 10);
        manager.getTransaction().begin();
        try {
            manager.persist(planeType);
            manager.persist(scheduleRecord);
            scheduleRecord.setDepartureAirport(newDepartureAirport).setDepartureTime(newDepartureTime);
            dao.update(scheduleRecord);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        FlightsScheduleRecord found = manager.createQuery("from FlightsScheduleRecord where flightName = :p", FlightsScheduleRecord.class)
                .setParameter("p", scheduleRecord.getFlightName())
                .getSingleResult();
        assertEquals("No update for DepartureAirport", newDepartureAirport, found.getDepartureAirport());
        assertEquals("No update for DepartureTime", newDepartureTime, found.getDepartureTime());
    }

    @Test
    public void delete() {
        FlightsScheduleRecord scheduleRecord = new FlightsScheduleRecord()
                .setFlightName("Y7 869")
                .setDepartureAirport(KJA)
                .setArriveAirport(SVX)
                .setPlaneType(planeType)
                .setDepartureTime(LocalTime.of(20, 30))
                .setArriveTime(LocalTime.of(1, 20))
                .setDays("ср сб");
        manager.getTransaction().begin();
        try {
            manager.persist(planeType);
            manager.persist(scheduleRecord);
            dao.delete(scheduleRecord);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        FlightsScheduleRecord found = manager.find(FlightsScheduleRecord.class, scheduleRecord.getFlightName());
        assertNull("Exist record in DB", found);
    }

    @Ignore
    @Test
    public void findById() {
    }

    @Ignore
    @Test
    public void findAll() {
    }
}