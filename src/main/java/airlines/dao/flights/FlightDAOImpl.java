package airlines.dao.flights;

import airlines.dao.FlightDAO;
import airlines.entities.aircrafts.PassengerPlane;
import airlines.entities.employees.Employee;
import airlines.entities.flights.Flight;
import airlines.entities.flights.enums.statuses.FlightStatusesEnum;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

public class FlightDAOImpl implements FlightDAO {

    private final EntityManager manager;

    public FlightDAOImpl(EntityManager manager) {
        this.manager = manager;
    }

    public void create(Flight flight) {
        manager.persist(flight);
    }

    public void update(Flight flight) {
        manager.merge(flight);
    }

    public void delete(Flight flight) {
        manager.remove(flight);
    }

    public Flight findById(int flightId) {
        return manager.createQuery("from Flight where id = :p", Flight.class)
                .setParameter("p", flightId)
                .getSingleResult();
    }

    public List<Flight> findByDateRange(Date beginDate, Date endDate) {
        return null;
    }

    public List<Flight> findAll() {
        return manager.createQuery("from Flight", Flight.class)
                .getResultList();
    }

    public PassengerPlane getPlaneByFlightId(int flightId) {
        PassengerPlane plane = null;
        Flight flight = findById(flightId);
        if (flight != null) {
            plane = flight.getPlane();
        }
        return plane;
    }

    public List<Employee> getCrewByFlightId(int flightId) {
        List<Employee> crewMembers = null;
        Flight flight = findById(flightId);
        if (flight != null) {
            crewMembers = flight.getCrewMembers();
        }
        return crewMembers;
    }

    public void setPlane(Flight flight, PassengerPlane plane) {
        flight.setPlane(plane);
        manager.merge(flight);
    }

    public void setCrew(Flight flight, List<Employee> crewMembers) {
        flight.setCrewMembers(crewMembers);
        manager.merge(flight);
    }

    public void changeFlightStatus(Flight flight, FlightStatusesEnum newFlightStatus) {
        flight.setStatusId(newFlightStatus);
        manager.merge(flight);
    }
}
