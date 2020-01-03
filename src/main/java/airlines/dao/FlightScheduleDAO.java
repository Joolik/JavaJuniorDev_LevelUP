package airlines.dao;


import airlines.entities.flights.FlightsScheduleRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
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
