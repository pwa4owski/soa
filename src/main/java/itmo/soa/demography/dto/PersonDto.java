package itmo.soa.demography.dto;


import itmo.soa.demography.model.Country;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @Min(value = 1)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @Valid
    private CoordinatesDto coordinates;

  //  private Date creationDate;

    @NotNull
    @Min(value = 1)
    private Long height;

    @NotNull
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private ZonedDateTime birthday;

    @NotNull
    @Min(value = 1)
    private Double weight;

    @NotNull
    @Valid
    private Country nationality;

    @NotNull
    @Valid
    private LocationDto location;

    @NotBlank
    private String hairColor;
}
