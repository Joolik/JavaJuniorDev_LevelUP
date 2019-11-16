package airlines.dao;


import airlines.entities.flights.FlightsScheduleRecord;

import java.util.List;

public interface FlightScheduleDAO {

    // создание расписания рейса
    void create(FlightsScheduleRecord flightsSchedule);

    // обновление расписания рейса
    void update(FlightsScheduleRecord flightsSchedule);

    // удаление расписания рейса
    void delete(FlightsScheduleRecord flightsSchedule);

    // поиск расписания рейса по идентификатору flightName
    FlightsScheduleRecord findById(String flightName);

    // полное расписание рейсов
    List<FlightsScheduleRecord> findAll();

}
