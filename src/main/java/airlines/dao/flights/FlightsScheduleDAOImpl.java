package airlines.dao.flights;

import airlines.dao.FlightScheduleDAO;
import airlines.entities.flights.FlightsScheduleRecord;

import javax.persistence.EntityManager;
import java.util.List;

public class FlightsScheduleDAOImpl implements FlightScheduleDAO {

    private final EntityManager manager;

    public FlightsScheduleDAOImpl(EntityManager manager) {
        this.manager = manager;
    }

    public void create(FlightsScheduleRecord flightsSchedule) {
        if (flightsSchedule.getArriveAirport().equals(flightsSchedule.getDepartureAirport())) {
            throw new IllegalArgumentException("Departure and arrive airports should not be equal!");
        }
        manager.persist(flightsSchedule);
    }

    public void update(FlightsScheduleRecord flightsSchedule) {
        if (flightsSchedule.getArriveAirport().equals(flightsSchedule.getDepartureAirport())) {
            throw new IllegalArgumentException("Departure and arrive airports should not be equal!");
        }
        manager.merge(flightsSchedule);
    }

    public void delete(FlightsScheduleRecord flightsSchedule) {
        manager.remove(flightsSchedule);
    }

    public FlightsScheduleRecord findById(String flightName) {
        return null;
    }

    public List<FlightsScheduleRecord> findAll() {
        return null;
    }
}
