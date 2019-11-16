package airlines.dao;

import airlines.entities.aircrafts.PassengerPlane;
import airlines.entities.employees.Employee;
import airlines.entities.flights.Flight;
import airlines.entities.flights.enums.statuses.FlightStatusesEnum;

import java.util.Date;
import java.util.List;

public interface FlightDAO {

    // создание рейса
    void create(Flight flight);

    // обновление рейса
    void update(Flight flight);

    // удаление рейса
    void delete(Flight flight);

    // поиск рейса по id
    Flight findById(int flightId);

    // поиск рейсов в диапазоне дат
    List<Flight> findByDateRange(Date beginDate, Date endDate);

    // поиск всех рейсов
    List<Flight> findAll();

    // получить самолет по рейсу
    PassengerPlane getPlaneByFlightId(int flightId);

    // получить список членов экипажа по рейсу
    List<Employee> getCrewByFlightId(int flightId);

    // добавить самолет к рейсу
    void setPlane(Flight flight, PassengerPlane plane);
//    void setPlane(int flightId, String plainRegNumber);

    // добавить экипаж к рейсу
    void setCrew(Flight flight, List<Employee> crewMembers);
//    void setCrew(int flightId, List<String> crewMembersIds);

    // изменить статус рейса
    void changeFlightStatus(Flight flight, FlightStatusesEnum newFlightStatus);
}
