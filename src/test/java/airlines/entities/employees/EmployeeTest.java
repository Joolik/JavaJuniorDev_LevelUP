package airlines.entities.employees;

import airlines.BaseTest;
import org.junit.Test;

import static airlines.entities.employees.enums.EmployeePositions.*;
import static org.junit.Assert.*;

public class EmployeeTest extends BaseTest {

    @Test
    public void entityEmployeeTest() {
        Employee pilot = new Employee(118, "Михайлов Александр Алексеевич", CAPTAIN);
        manager.getTransaction().begin();
        try {
            manager.persist(pilot);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        assertNotNull(manager.find(Employee.class, 118));
    }

}