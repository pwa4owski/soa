package itmo.soa.demography.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CoordinatesDto {

    @Min(value = -234, message = "Должно быть больше или равно -234")
    private int x;

    @NotNull
    @Max(value = 456, message = "Должно быть меньше или равно -234")
    private Integer y;
}
