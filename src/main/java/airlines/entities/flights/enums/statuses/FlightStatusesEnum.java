package airlines.entities.flights.enums.statuses;

public enum FlightStatusesEnum {

    NEW("Формируется"),
    READY("Сформирован"),
    CHECK_IN("Регистрация"),
    BOARDING_COMPLETED("Посадка закончена"),
    DEPARTED("Отправлен"),
    ARRIVED("Прибыл"),
    CANCELED("Отменен"),
    DELAYED("Задержан"),
    COMPLETED("Завершен");

    private String value;

    FlightStatusesEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
