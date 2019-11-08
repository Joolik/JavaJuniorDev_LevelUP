package airlines.entities.aircrafts;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/*
 * Пассажирские самолеты
 */

@Entity
@Table(name = "passenger_planes")
@Data
public class PassengerPlane {

    // Бортовой номер воздушного средства
    @Id
    @Column
    private String id;

    //TODO do Enum
    // Тип воздушного средства
    @Column(nullable = false, length = 30)
    private String type;

    // Пассажировместимость
    @Column(name = "seating_capacity", nullable = false)
    private int seatingCapacity;

    // Дата последнего тех.осмотра
    @Column(name = "last_service_date")
    private Date lastServiceDate;

    public PassengerPlane() {
    }

    public PassengerPlane(String id, String type, int seatingCapacity) {
        this.id = id;
        this.type = type;
        this.seatingCapacity = seatingCapacity;
    }

    @Override
    public String toString() {
        return "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", seatingCapacity=" + seatingCapacity +
                ", lastServiceDate=" + lastServiceDate.toString();
    }

}
