package airlines.dao;

import airlines.entities.aircrafts.PassengerPlane;
import airlines.entities.aircrafts.PlaneType;

import java.util.List;

public interface PlaneTypeDAO {

    // добавление типа самолета
    void create(PlaneType planeType);

    // обновление данных о типе самолете
    void update(PlaneType planeType);

    // удаление типа самолета
    void delete(PlaneType planeType);

    // поиск типа самолета по id
    PlaneType findById(String id);

    // список всех типов самолетов
    List<PlaneType> findAll();
}
