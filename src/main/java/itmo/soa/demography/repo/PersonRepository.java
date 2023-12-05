package itmo.soa.demography.repo;

import itmo.soa.demography.model.Person;
import itmo.soa.demography.util.Pageable;
import itmo.soa.demography.util.Page;
import jakarta.ejb.LocalBean;

import java.util.List;
import java.util.Optional;

@LocalBean
public interface PersonRepository {

    Person addPerson(Person person);

    Person updatePersonById(Long id, Person person);

    void deletePersonById(Long id);

    Optional<Person> getPersonById(Long id);

    List<Person> getAll();

    Page getPage(Pageable pageable);

    void deletePersonsWithWeightEqual(Double weight);

    List<Person> getPersonsWithWeightLessThan(double weight);
}
