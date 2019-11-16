package airlines.entities.flights;

import airlines.entities.aircrafts.PassengerPlane;
import airlines.entities.employees.Employee;
import airlines.entities.flights.enums.statuses.FlightStatusesEnum;
import airlines.entities.flights.enums.statuses.FlightStatusesConverter;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/*
 * Таблица всех рейсов по датам
 */

@Entity
@Table(name = "flights",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"flight_name", "date"})})
@Data @Accessors(chain = true)
public class Flight {

    // уникальный id рейса
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    // идентификатор регулярного рейса
    @ManyToOne(optional = true)
    @JoinColumn(name = "flight_name", nullable = false)
    private FlightsScheduleRecord flightName;

    // дата рейса (без времени)
    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    // статус рейса
    @Column(name = "status_id", nullable = false)
    @Convert(converter = FlightStatusesConverter.class)
    private FlightStatusesEnum statusId;

    // самолет
    @OneToOne
    private PassengerPlane plane;

    // экипаж
    @ManyToMany
    @JoinTable(name = "crews",
            joinColumns = @JoinColumn(name = "flight_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> crewMembers;

    public Flight() {
    }

    public Flight(FlightsScheduleRecord flightName, Date date, FlightStatusesEnum statusId, PassengerPlane plane, List<Employee> crewMembers) {
        this.flightName = flightName;
        this.date = date;
        this.statusId = statusId;
        this.plane = plane;
        this.crewMembers = crewMembers;
    }

    @Override
    public String toString() {
        // TODO crewMembers
        return "Flight{" +
                "id=" + id +
                ", flightName=" + flightName +
                ", date=" + date +
                ", statusId=" + statusId +
                ", plane=" + plane +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return id == flight.id &&
                statusId == flight.statusId &&
                Objects.equals(flightName, flight.flightName) &&
                Objects.equals(date, flight.date) &&
                Objects.equals(plane, flight.plane) &&
                Objects.equals(crewMembers, flight.crewMembers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, flightName, date, statusId, plane, crewMembers);
    }
}
