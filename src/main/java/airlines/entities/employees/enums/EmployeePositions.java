package airlines.entities.employees.enums;

public enum EmployeePositions {

    CAPTAIN("командир воздушного судна"),
    FIRST_OFFICER("второй пилот"),
    PURSER("старший бортпроводник"),
    FLIGHT_ATTENDANT("бортпроводник"),
    MECHANIC("механик"),
    MANAGER("диспетчер");

    private String value;

    EmployeePositions(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
