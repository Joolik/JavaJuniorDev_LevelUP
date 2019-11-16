package airlines.entities.flights;

import airlines.entities.aircrafts.PlaneType;
import airlines.entities.flights.enums.AirportCodesEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
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
    @Column(name = "departure_airport", length = 3, nullable = false)
    @Enumerated(EnumType.STRING)
    private AirportCodesEnum departureAirport;

    // аэропорт назначения
    @Column(name = "arrive_airport", length = 3, nullable = false)
    @Enumerated(EnumType.STRING)
    private AirportCodesEnum arriveAirport;

    // время отправления
    @Column(name = "departure_time", nullable = false)
    private LocalTime departureTime;

    // время прибытия
    @Column(name = "arrive_time", nullable = false)
    private LocalTime arriveTime;

    // тип самолета
    @ManyToOne(optional = false)
    @JoinColumn(name = "plane_type")
    private PlaneType planeType;

    // TODO пока дни недели просто перечисляются в строке
    // дни вылета
    @Column(length = 30, nullable = false)
    private String days;

    // в таблице flights может быть несколько записей на разные даты
    @OneToMany(mappedBy = "flightName", fetch = FetchType.EAGER)
    private List<Flight> flights;

}
