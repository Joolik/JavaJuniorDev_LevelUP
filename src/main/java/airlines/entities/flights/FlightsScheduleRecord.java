package airlines.entities.flights;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

/*
 * Таблица с расписанием регулярных рейсов (дни недели, время и место вылета/прилета)
 */

@Entity
@Table(name = "schedule")
@Data
@Accessors(chain = true)
public class FlightsScheduleRecord {

    // уникальный идентификатор регулярного рейса
    @Id
    @Column(name = "flight_name", length = 20)
    private String flightName;

    // аэропорт отправления
    @Column(name = "departure_airport", length = 3)
    private String departureAirport;

    // аэропорт назначения
    @Column(name = "arrive_airport", length = 3)
    private String arriveAirport;

    // время отправления
    @Column(name = "departure_time")
    private LocalTime departureTime;

    // время прибытия
    @Column(name = "arrive_time")
    private LocalTime arriveTime;

    // тип самолета
    // TODO do Enum
    @Column(name = "plane_type")
    private String planeType;

    // TODO пока дни недели просто перечисляются в строке
    // дни вылета
    @Column(length = 30)
    private String days;

    // в таблице flights может быть несколько записей на разные даты
    @OneToMany(mappedBy = "flightName", fetch = FetchType.EAGER)
    private List<Flight> flights;

}
