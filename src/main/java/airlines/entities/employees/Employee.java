package airlines.entities.employees;

import airlines.entities.flights.Flight;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/*
 * Сотрудники авиакомпании
 */

@Entity
@Table(name = "employees")
@Data
public class Employee {

    // табельный номер сотрудника
    @Id
    @Column
    private int id;

    // ФИО
    @Column(nullable = false)
    private String name;

    // должность
    @Column(nullable = false)
    private String position;

    @ManyToMany
    @JoinTable(name = "crews",
            joinColumns = @JoinColumn(name="employee_id"),
            inverseJoinColumns = @JoinColumn(name = "flight_id"))
    private List<Flight> employeeFlights;

    public Employee() {
    }

    public Employee(int id, String name, String position) {
        this.id = id;
        this.name = name;
        this.position = position;
    }
}
