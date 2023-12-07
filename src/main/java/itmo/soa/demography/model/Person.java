package itmo.soa.demography.model;


import itmo.soa.demography.dto.PersonDto;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(schema = "soa", name = "person")
public class Person implements Serializable {

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
    @JsonbDateFormat(value = "yyyy-MM-dd")
    private Date creationDate;

    @NotNull
    @Min(value = 1)
    private Long height;

    @NotNull
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
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

    public static Person createFromDto(@Valid PersonDto personDto) throws IllegalArgumentException {
        Coordinates coordinates = Coordinates.builder()
                .x(personDto.getCoordinates().getX())
                .y(personDto.getCoordinates().getY())
                .build();
        Location location = Location.builder()
                .name(personDto.getLocation().getName())
                .x(personDto.getLocation().getX())
                .y(personDto.getLocation().getY())
                .build();
        Person person;
        try {
            person = Person.builder()
                    .coordinates(coordinates)
                    .birthday(personDto.getBirthday())
                    .hairColor(personDto.getHairColor())
                    .height(personDto.getHeight())
                    .location(location)
                    .nationality(Country.valueOf(personDto.getNationality().toUpperCase()))
                    .name(personDto.getName())
                    .weight(personDto.getWeight())
                    .build();
        }
        catch (Exception e) {
            throw new IllegalArgumentException("неверный формат");
        }

        return person;
    }
}
