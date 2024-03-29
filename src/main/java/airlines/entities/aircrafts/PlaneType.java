package airlines.entities.aircrafts;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

/*
 * Типы самолетов
 */

@Entity
@Table(name = "plane_types")
@Data @Accessors(chain = true)
public class PlaneType {

    // Внутренний идентификатор типа самолета
    @Id
    @Column(name = "id", length = 20)
    private String id;

    // Полное название типа самолета
    @Column(name = "full_name", length = 50, nullable = false)
    private String name;

    // Пассажировместимость
    @Column(name = "seating_capacity", nullable = false)
    private int seatingCapacity;

    @OneToMany(mappedBy = "type", fetch = FetchType.EAGER)
    private List<PassengerPlane> planes;

    public PlaneType() {
    }

    public PlaneType(String id, String name, int seatingCapacity) {
        this.id = id;
        this.name = name;
        this.seatingCapacity = seatingCapacity;
    }

    @Override
    public String toString() {
        return "PlaneType{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", seatingCapacity=" + seatingCapacity +
                '}';
    }
}
