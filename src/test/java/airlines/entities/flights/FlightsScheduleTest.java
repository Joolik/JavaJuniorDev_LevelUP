package airlines.entities.flights;

import airlines.BaseTest;
import airlines.entities.aircrafts.PlaneType;
import org.junit.Test;

import java.time.LocalTime;

import static airlines.entities.flights.enums.AirportCodes.*;
import static org.junit.Assert.*;

public class FlightsScheduleTest extends BaseTest {

    @Test
    public void entitySchedule() {

        PlaneType planeType1 = new PlaneType("B737-800", "Boeing 737-800", 180);

        FlightsScheduleRecord scheduleRecord1, scheduleRecord2;

        manager.persist(planeType1);

        scheduleRecord1 = new FlightsScheduleRecord().setFlightName("Y7 869")
                .setDepartureAirport(KJA)
                .setArriveAirport(SVX)
                .setPlaneType(planeType1)
                .setDepartureTime(LocalTime.of(20,30))
                .setArriveTime(LocalTime.of(1,20))
                .setDays("ср сб");
        scheduleRecord2 = new FlightsScheduleRecord().setFlightName("Y5 02")
                .setDepartureAirport(LED)
                .setArriveAirport(VVO)
                .setPlaneType(planeType1)
                .setDepartureTime(LocalTime.of(22,50))
                .setArriveTime(LocalTime.of(9, 05))
                .setDays("чт");

        manager.getTransaction().begin();
        try {
            manager.persist(planeType1);
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