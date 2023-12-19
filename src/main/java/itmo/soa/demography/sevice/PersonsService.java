package itmo.soa.demography.sevice;

import itmo.soa.demography.dto.LocationDto;
import itmo.soa.demography.dto.PersonDto;
import itmo.soa.demography.model.Location;
import itmo.soa.demography.model.Person;
import itmo.soa.demography.repo.PersonRepository;
import itmo.soa.demography.util.*;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.NotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Stateless
@Named
public class PersonsService {

    @Inject
    PersonRepository repo;

    @Inject
    FilterSortService filterSortService;

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
        person = repo.updatePersonById(id, person);
        if(person == null)
             throw new NotFoundException("нет элемента с id " + id);
        return person;
    }

    public void deletePersonsWithLessWeight(Double weight) {
        repo.deletePersonsWithWeightEqual(weight);
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

    @Transactional
    public void deletePerson(Long id) {
         if(!repo.deletePersonById(id)) {
             throw new NotFoundException("Нет элемента с id " + id);
         }
    }

    @Transactional
    public Page getPage(List<String> filters, List<String> sorts, int page, int size) {
        List<Filter> filterList = filters.stream()
                .map(s -> filterSortService.parseFilter(s))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<Sort> sortList = sorts.stream()
                .map(s -> filterSortService.parseSort(s))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Pageable pagable = Pageable.builder()
                .page(page)
                .size(size)
                .filters(filterList)
                .sorts(sortList)
                .build();

        return repo.getPage(pagable);

    }

    public List<Person> getPersonsWithWeightLessThan(double weight) {
        return repo.getPersonsWithWeightLessThan(weight);
    }
}