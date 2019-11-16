package airlines.entities.employees;

import airlines.entities.employees.enums.EmployeePositionsEnum;
import airlines.entities.flights.Flight;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

/*
 * Сотрудники авиакомпании
 */

@Entity
@Table(name = "employees")
@Data @Accessors(chain = true)
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
    @Enumerated(EnumType.STRING)
    private EmployeePositionsEnum position;

    @ManyToMany
    @JoinTable(name = "crews",
            joinColumns = @JoinColumn(name="employee_id"),
            inverseJoinColumns = @JoinColumn(name = "flight_id"))
    private List<Flight> employeeFlights;

    public Employee() {
    }

    public Employee(int id, String name, EmployeePositionsEnum position) {
        this.id = id;
        this.name = name;
        this.position = position;
    }
}
