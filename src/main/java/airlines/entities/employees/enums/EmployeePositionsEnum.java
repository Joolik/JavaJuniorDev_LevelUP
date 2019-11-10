package airlines.entities.employees.enums;

public enum EmployeePositionsEnum {

    CAPTAIN("командир воздушного судна"),
    FIRST_OFFICER("второй пилот"),
    PURSER("старший бортпроводник"),
    FLIGHT_ATTENDANT("бортпроводник"),
    MECHANIC("механик");

    private String value;

    EmployeePositionsEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
