package airlines.entities.flights.enums.statuses;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static airlines.entities.flights.enums.statuses.FlightStatusesEnum.*;

@Converter
public class FlightStatusesConverter implements AttributeConverter<FlightStatusesEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(FlightStatusesEnum attribute) {
        if (attribute == null) {
            return null;
        }
        switch (attribute) {
            case NEW:
                return 1;
            case READY:
                return 2;
            case CHECK_IN:
                return 3;
            case BOARDING_COMPLETED:
                return 4;
            case DEPARTED:
                return 5;
            case ARRIVED:
                return 6;
            case CANCELED:
                return 7;
            case DELAYED:
                return 8;
            case COMPLETED:
                return 9;
            default:
                throw new IllegalArgumentException(attribute + " not supported");
        }
    }

    @Override
    public FlightStatusesEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        switch (dbData) {
            case 1:
                return NEW;
            case 2:
                return READY;
            case 3:
                return CHECK_IN;
            case 4:
                return BOARDING_COMPLETED;
            case 5:
                return DEPARTED;
            case 6:
                return ARRIVED;
            case 7:
                return CANCELED;
            case 8:
                return DELAYED;
            case 9:
                return COMPLETED;
            default:
                throw new IllegalArgumentException(dbData + " not supported");
        }
    }
}
