package itmo.soa.demography.dto;


import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonDto {

    //@NotNull
    @Min(value = 1)
    private Long id;

    @NotBlank(message = "Не должно быть пустым")
    @Size(max = 255, message = "Слишком длинная строка")
    private String name;

    @NotNull
    @Valid
    private CoordinatesDto coordinates;

  //  private Date creationDate;

    @NotNull
    @Min(value = 1, message = "Должно быть положительный")
    private Long height;

    @NotNull
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private ZonedDateTime birthday;

    @NotNull
    @Min(value = 1, message = "Должно быть положительным")
    private Double weight;

    @NotBlank(message = "Не должно быть пустым")
    @Pattern(regexp = "GERMANY|RUSSIA|UNITED_KINGDOM|THAILAND", flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "неверно введена страна")
    //@Valid
    //@EnumNamePattern(regexp = "GERMANY|RUSSIA|UNITED_KINGDOM|THAILAND")
    private String nationality;

    @NotNull
    @Valid
    private LocationDto location;

    @NotBlank(message = "Не должно быть пустым")
    @Size(max = 255, message = "Слишком длинная строка")
    private String hairColor;
}
