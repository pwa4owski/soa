package itmo.soa.demography.sevice;

import itmo.soa.demography.dto.LocationDto;
import itmo.soa.demography.dto.PersonDto;
import itmo.soa.demography.model.Location;
import itmo.soa.demography.model.Person;
import itmo.soa.demography.repo.PersonRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@Stateless
@Named
public class PersonsService {

    @Inject
    PersonRepository repo;

    public List<Person> getAll() {
        return repo.getAll();
    }

    public Person addPerson(PersonDto personDto) {
        Person person = Person.createFromDto(personDto);
        return repo.addPerson(person);
    }

    public Person getById(Long id) {
        return repo.getPersonById(id)
                .orElseThrow(() -> new NotFoundException("нет элемента с id " + id));
    }

    public Person updatePersonById(long id, PersonDto personDto) {
        Person person = Person.createFromDto(personDto);
        return repo.updatePersonById(id, person);
    }

    public void deletePersonsWithLessWeight(Double weight) {
        repo.deletePersonsWithLessWeight(weight);
    }


    public void deletePersonsByLocation(LocationDto locationDto) {
        Location location = Location.builder()
                .name(locationDto.getName())
                .x(locationDto.getX())
                .y(locationDto.getY())
                .build();

        repo.getAll().stream()
                .filter(person -> person.getLocation().equals(location))
                .forEach(p -> repo.deletePersonById(p.getId()));
    }

    public void deletePerson(Long id) {
        repo.deletePersonById(id);
    }
}