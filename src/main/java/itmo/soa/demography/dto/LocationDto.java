package itmo.soa.demography.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LocationDto {

    @NotNull
    private Integer x;

    @NotNull
    private Integer y;

    @NotNull
    private String name;


}
