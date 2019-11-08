package airlines.entities.flights;

import airlines.entities.aircrafts.PassengerPlane;
import airlines.entities.employees.Employee;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/*
 * Таблица всех рейсов по датам
 */

@Entity
@Table(name = "flights")
@Data
public class Flight {

    // уникальный id рейса
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    // идентификатор регулярного рейса
    @ManyToOne(optional = true)
    @JoinColumn(name = "flight_name")
    private FlightsScheduleRecord flightName;

    // дата рейса (без времени)
    @Column
    @Temporal(TemporalType.DATE)
    private Date date;

    // статус рейса
    //TODO do Enum
    @Column(name = "status_id", nullable = false)
    private int statusId;

    // самолет
    @OneToOne
    private PassengerPlane plane;

    // экипаж
    @ManyToMany
    @JoinTable(name = "crews",
            joinColumns = @JoinColumn(name="flight_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> crewMembers;

    public Flight() {
    }

    public Flight(FlightsScheduleRecord flightName, Date date, int statusId, PassengerPlane plane, List<Employee> crewMembers) {
        this.flightName = flightName;
        this.date = date;
        this.statusId = statusId;
        this.plane = plane;
        this.crewMembers = crewMembers;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", flightName=" + flightName +
                ", date=" + date +
                ", statusId=" + statusId +
                ", plane=" + plane +
                ", crewMembers=" + crewMembers +
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
