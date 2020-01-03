package airlines.dao.employees;

import airlines.dao.EmployeeDAO;
import airlines.entities.employees.Employee;
import airlines.entities.employees.enums.EmployeePositions;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    private final EntityManager manager;

    @Autowired
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

    public List<Employee> findByPosision(EmployeePositions posision) {
        return null;
    }
}
