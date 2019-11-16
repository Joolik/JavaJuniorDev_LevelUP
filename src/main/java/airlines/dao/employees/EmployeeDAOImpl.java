package airlines.dao.employees;

import airlines.dao.EmployeeDAO;
import airlines.entities.employees.Employee;
import airlines.entities.employees.enums.EmployeePositionsEnum;

import javax.persistence.EntityManager;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    private final EntityManager manager;

    public EmployeeDAOImpl(EntityManager manager) {
        this.manager = manager;
    }

    public void create(Employee employee) {
        manager.persist(employee);
    }

    public void update(Employee employee) {
        manager.merge(employee);
    }

    public void delete(Employee employee) {
        manager.remove(employee);
    }

    public Employee findById(String id) {

        return null;
    }

    public List<Employee> findByPosision(EmployeePositionsEnum posision) {
        return null;
    }
}
