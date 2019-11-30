package airlines.dao;

import airlines.entities.employees.Employee;
import airlines.entities.employees.enums.EmployeePositions;

import java.util.List;

public interface EmployeeDAO {

    // добавление сотрудника
    void create(Employee employee);

    // обновление данных сотрудника
    void update(Employee employee);

    // удаление сотрудника
    void delete(Employee employee);

    // поиск сотрудника по id
    Employee findById(String id);

    // список сотрудников по должности
    List<Employee> findByPosision(EmployeePositions posision);

}
