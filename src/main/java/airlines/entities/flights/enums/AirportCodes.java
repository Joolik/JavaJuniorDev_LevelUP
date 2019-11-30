package airlines.entities.flights.enums;

public enum AirportCodes {

    LED("Санкт-Петербург, Пулково"),
    DME("Москва, Домодедово"),
    SVO("Москва, Шереметьево"),
    VVO("Владивосток"),
    KJA("Красноярск"),
    SVX("Екатеринбург"),
    PEK("Пекин, Beijing Capital Int.");

    private String value;

    AirportCodes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
