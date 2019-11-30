package airlines.entities.employees;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@Accessors(chain = true)
public class User {

    @Id
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "boolean default false")
    private Boolean admin;

    @OneToOne
    private Employee employee;
}
