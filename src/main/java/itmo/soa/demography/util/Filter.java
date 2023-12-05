package itmo.soa.demography.util;

import itmo.soa.demography.model.Country;
import itmo.soa.demography.util.wrappers.*;
import lombok.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Filter {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");

    public Filter (String fieldName, String strVal, Sign sign) {
        switch (fieldName) {
            case "id":
            case "height":
                this.valueWrapper = new LongWrapper(Long.parseLong(strVal));
                break;
            case "coordinates.x":
            case "coordinates.y":
            case "location.x":
            case "location.y":
                this.valueWrapper = new IntegerWrapper(Integer.parseInt(strVal));
                break;
            case "name":
            case "location.name":
            case "hairColor":
                this.valueWrapper = new StringWrapper(strVal);
                break;
            case "creationDate":
                try {
                    this.valueWrapper = new DateWrapper((sdf.parse(strVal)));
                } catch (ParseException e) {
                    throw new IllegalArgumentException("Failed to parse Date from the input string.", e);
                }
                break;
            case "birthday":
                try {
                    this.valueWrapper = new ZonedDateTimeWrapper(ZonedDateTime.parse(strVal, formatter));
                } catch (Exception e) {
                    throw new IllegalArgumentException("Failed to parse  ZonedDateTime from the input string.", e);
                }
                break;
            case "nationality":
                this.valueWrapper = new CountryWrapper(Country.valueOf(strVal.toUpperCase()));
                break;
            default:
                throw new IllegalArgumentException("Нет такого поля!");
        }
        this.pathToField = Arrays.stream(fieldName.split("\\.")).collect(Collectors.toList());
        this.sign = sign;
    }

    private List<String> pathToField;

    private Sign sign;

    private AbstractWrapper<?> valueWrapper;
}
