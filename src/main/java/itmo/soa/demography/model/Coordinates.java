package itmo.soa.demography.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(schema = "soa", name = "coordinates")
@Builder
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @Min(value = -234)
    @Column(name = "x")
    private int x;

    @NotNull
    @Max(value = 456)
    @Column(name = "y")
    private Integer y;
}
