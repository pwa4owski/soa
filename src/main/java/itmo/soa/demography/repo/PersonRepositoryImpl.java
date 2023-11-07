package itmo.soa.demography.repo;

import itmo.soa.demography.model.Person;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Stateless
public class PersonRepositoryImpl implements PersonRepository{

    EntityManager entityManager  = Persistence
            .createEntityManagerFactory("primary")
            .createEntityManager();

    //Session session = entityManager.unwrap(org.hibernate.Session.class);

    @Override
    @Transactional
    public Person addPerson(Person person) {
        entityManager.getTransaction().begin();
        entityManager.persist(person);
        entityManager.getTransaction().commit();
        return person;
    }

    @Override
    @Transactional
    public Person updatePersonById(Long id, Person person) {
        entityManager.getTransaction().begin();
        if(entityManager.find(Person.class, id) == null) {
            throw new NotFoundException("нет элемента с id " + id);
        }
        person.setId(id);

        Person updatedPerson = entityManager.merge(person);
        updatedPerson.setCreationDate(new Date());

        entityManager.getTransaction().commit();
        return updatedPerson;
    }

    @Override
    @Transactional
    public void deletePersonById(Long id) {
        entityManager.getTransaction().begin();
        Person person = entityManager.find(Person.class, id);
        if(person == null){
            throw new NotFoundException("нет элемента с id " + id);
        }
        entityManager.remove(person);
        entityManager.getTransaction().commit();
    }

    @Override
    @Transactional
    public Optional<Person> getPersonById(Long id) {
        Person person = entityManager.find(Person.class, id);
        return Optional.of(person);
    }

    @Override
    @Transactional
    public List<Person> getAll() {
        String jpql = "SELECT e FROM Person e";
        TypedQuery<Person> query = entityManager.createQuery(jpql, Person.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void deletePersonsWithLessWeight(Double weight) {
        entityManager.getTransaction().begin();
        String jpql = "DELETE FROM Person e WHERE e.weight < :weight";
        entityManager.createQuery(jpql)
                .setParameter("weight", weight)
                .executeUpdate();
        entityManager.getTransaction().commit();
    }
}
