package itmo.soa.demography.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LocationDto {

    @NotNull(message = "Должно быть заполнено")
    private Integer x;

    @NotNull(message = "Должно быть заполнено")
    private Integer y;

    @NotBlank(message = "Не должно быть пустым")
    @Size(max = 255, message = "Слишком длинная строка")
    private String name;


}
