package airlines.entities.flights.enums;

public enum AirportCodesEnum {

    LED("Санкт-Петербург, Пулково"),
    DME("Москва, Домодедово"),
    SVO("Москва, Шереметьево"),
    VVO("Владивосток"),
    KJA("Красноярск"),
    SVX("Екатеринбург"),
    PEK("Пекин, Beijing Capital Int.");

    private String value;

    AirportCodesEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
