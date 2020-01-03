package airlines.dao;

import airlines.entities.aircrafts.PassengerPlane;
import airlines.entities.employees.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerPlaneDAO {

    // добавление самолета
    void create(PassengerPlane plane);

    // обновление данных о самолете
    void update(PassengerPlane plane);

    // удаление самолета
    void delete(PassengerPlane plane);

    // поиск самолета по reg_number
    PassengerPlane findById(String plainRegNumber);

    // поиск самолетов по типу
    List<PassengerPlane> findByType(String plainType);

    // список всех самолетов
    List<PassengerPlane> findAll();
}
