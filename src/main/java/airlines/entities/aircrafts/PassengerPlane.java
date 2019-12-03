package airlines.entities.aircrafts;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/*
 * Пассажирские самолеты авиакомпании
 */

@Entity
@Table(name = "planes")
@Data @Accessors(chain = true)
public class PassengerPlane {

    // бортовой регистрационный номер самолета
    @Id
    @Column(name = "reg_number", length = 10)
    private String plainRegNumber;

    // тип самолета
    @ManyToOne(optional = false)
    @JoinColumn(name = "type_id")
    private PlaneType type;

    // дата последнего тех.осмотра
    @Column(name = "last_service_date")
    private Date lastServiceDate;

    public PassengerPlane() {
    }

    public PassengerPlane(String plainRegNumber, PlaneType type) {
        this.plainRegNumber = plainRegNumber;
        this.type = type;
    }

    @Override
    public String toString() {
        return "plainRegNumber='" + plainRegNumber + '\'';
//                ", type='" + type.toString() + '\'' +
//                ", lastServiceDate=" + lastServiceDate.toString();
    }

}
