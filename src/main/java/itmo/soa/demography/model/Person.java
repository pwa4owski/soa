package itmo.soa.demography.model;


import itmo.soa.demography.dto.Country;
import itmo.soa.demography.dto.PersonDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(schema = "soa", name = "person")
public class  Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "coordinates_id", referencedColumnName = "id")
    private Coordinates coordinates;

    @Generated
    @CreationTimestamp
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
    @Enumerated(EnumType.STRING)
    private Country nationality;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @NotBlank
    private String hairColor;

    public static Person createFromDto(@Valid PersonDto personDto) {
        Coordinates coordinates = Coordinates.builder()
                .x(personDto.getCoordinatesDto().getX())
                .y(personDto.getCoordinatesDto().getY())
                .build();
        Location location = Location.builder()
                .name(personDto.getLocationDto().getName())
                .x(personDto.getLocationDto().getX())
                .y(personDto.getLocationDto().getY())
                .build();
        return Person.builder()
                .coordinates(coordinates)
                .birthday(personDto.getBirthday())
                .hairColor(personDto.getHairColor())
                .height(personDto.getHeight())
                .location(location)
                .nationality(personDto.getNationality())
                .name(personDto.getName())
                .weight(personDto.getWeight())
                .build();
    }
}
