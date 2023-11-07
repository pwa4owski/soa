package itmo.soa.demography.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.Date;

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
    private CoordinatesDto coordinatesDto;

    @Generated
    private Date creationDate;

    @NotNull
    @Min(value = 1)
    private Long height;

    @NotNull
    private ZonedDateTime birthday;

    @NotNull
    @Min(value = 1)
    private Double weight;

    @NotNull
    @Valid
    private Country nationality;

    @NotNull
    @Valid
    private LocationDto locationDto;

    @NotBlank
    private String hairColor;
}