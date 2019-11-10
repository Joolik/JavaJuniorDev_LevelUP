package airlines.entities.flights;

import airlines.entities.BaseTest;
import airlines.entities.aircrafts.PassengerPlane;
import airlines.entities.aircrafts.PlaneType;
import airlines.entities.employees.Employee;
import airlines.entities.flights.enums.AirportCodesEnum;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static airlines.entities.employees.enums.EmployeePositionsEnum.*;
import static airlines.entities.flights.enums.AirportCodesEnum.*;
import static airlines.entities.flights.enums.statuses.FlightStatusesEnum.*;

public class FlightTest extends BaseTest {

    @Test
    public void entityFlight() throws ParseException {

        PlaneType planeType1 = new PlaneType("B737-800", "Boeing 737-800", 180);

        FlightsScheduleRecord scheduleRecord1, scheduleRecord2;
        scheduleRecord1 = new FlightsScheduleRecord().setFlightName("Y7 869")
                .setDepartureAirport(KJA)
                .setArriveAirport(SVX)
                .setPlaneType(planeType1)
                .setDepartureTime(LocalTime.of(20, 30))
                .setArriveTime(LocalTime.of(1, 20))
                .setDays("ср сб");
        scheduleRecord2 = new FlightsScheduleRecord().setFlightName("Y5 02")
                .setDepartureAirport(LED)
                .setArriveAirport(VVO)
                .setPlaneType(planeType1)
                .setDepartureTime(LocalTime.of(22, 50))
                .setArriveTime(LocalTime.of(9, 05))
                .setDays("чт");


        PassengerPlane plane = new PassengerPlane("PL5078", planeType1);

        Employee pilot1 = new Employee(118, "Михайлов Александр Алексеевич", CAPTAIN);
        Employee pilot2 = new Employee(95, "Тулин Виктор Петрович", FIRST_OFFICER);
        Employee airHostess = new Employee(156, "Свиридова Елена Дмитриевна", PURSER);

        List<Employee> crew1 = new ArrayList<>();
        crew1.add(pilot1);
        crew1.add(pilot2);
        crew1.add(airHostess);

        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date date1 = format.parse("2019-11-08");
        Date date2 = format.parse("2019-12-30");
        Flight flight1 = new Flight(scheduleRecord1, date1, COMPLETED, plane, crew1);
        Flight flight2 = new Flight(scheduleRecord1, date2, CANCELED, null, null);
        Flight flight3 = new Flight(scheduleRecord2, date2, NEW, plane, crew1);

        manager.getTransaction().begin();
        try {

            manager.persist(planeType1);

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