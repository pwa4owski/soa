package itmo.soa.demography.repo;

import itmo.soa.demography.model.Person;
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

    void deletePersonsWithLessWeight(Double weight);
}
