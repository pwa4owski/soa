package itmo.soa.demography.repo;

import itmo.soa.demography.model.Person;
import itmo.soa.demography.util.*;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.*;

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
    public void deletePersonsWithWeightEqual(Double weight) {
        entityManager.getTransaction().begin();
        String jpql = "DELETE FROM Person e WHERE e.weight = :weight";
        entityManager.createQuery(jpql)
                .setParameter("weight", weight)
                .executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Override
    @Transactional
    public  Page getPage(Pageable pagable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> flatQuery = criteriaBuilder.createQuery(Person.class);
        Root<Person> root = flatQuery.from(Person.class);

        CriteriaQuery<Person> select = flatQuery.select(root);

        List<Predicate> predicates = new ArrayList<>();

        for(Filter filter : pagable.getFilters()) {

            Path path = root;
            for(String nextField : filter.getPathToField()) {
                path = path.get(nextField);
            }

            switch (filter.getSign()) {
                case EQ:
                    predicates.add(criteriaBuilder
                            .equal(
                                    path,
                                    filter.getValueWrapper().getValue())
                    );
                    break;
                case NE:
                    predicates.add(criteriaBuilder
                            .notEqual(
                                    path,
                                    filter.getValueWrapper().getValue()
                            )
                    );
                    break;
                case GT:
                    predicates.add(criteriaBuilder
                            .greaterThan(
                                    path,
                                    criteriaBuilder.literal(
                                            filter.getValueWrapper().getValue()
                                    ).as(filter.getValueWrapper().getTypeClass())
                            )
                    );
                    break;
                case LT:
                    predicates.add(criteriaBuilder
                            .lessThan(
                                    path,
                                    criteriaBuilder.literal(
                                            filter.getValueWrapper().getValue()
                                    ).as(filter.getValueWrapper().getTypeClass())
                            )
                    );
                    break;
                case GTE:
                    predicates.add(criteriaBuilder
                            .greaterThanOrEqualTo(
                                    path,
                                    criteriaBuilder.literal(
                                            filter.getValueWrapper().getValue()
                                    ).as(filter.getValueWrapper().getTypeClass())
                            )
                    );
                    break;
                case LTE:
                    predicates.add(criteriaBuilder
                            .lessThanOrEqualTo(
                                    path,
                                    criteriaBuilder.literal(
                                            filter.getValueWrapper().getValue()
                                    ).as(filter.getValueWrapper().getTypeClass())
                            )
                    );
                    break;
                default:
                    throw new IllegalStateException("Неучтенный знак сравнения!");
            }
        }

        select.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        List<Order> orderList = new ArrayList<>();
        for(Sort sort : pagable.getSorts()) {
            Path<Person> path = root;
            for(String field : sort.getPathToField()) {
                path = path.get(field);
            }
            if(sort.isAsc()){
                orderList.add(criteriaBuilder.asc(path));
            }
            else {
                orderList.add(criteriaBuilder.desc(path));
            }
        }
        select.orderBy(orderList);

        int offset = pagable.getSize() * pagable.getPage();
        int limit = pagable.getSize();
        List<Person> notFiletedList = entityManager
                .createQuery(select)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

        int count = entityManager.createQuery(select).getResultList().size();
        int totalPages = count / pagable.getSize() +
                (count % pagable.getSize() > 0 ? 1 : 0);

        Page page = Page.builder()
                .totalPages(totalPages)
                .content(notFiletedList)
                .totalElements(count)
                .pageSize(pagable.getSize())
                .last(pagable.getPage() + 1 == totalPages)
                .pageNumber(pagable.getPage())
                .build();

        return page;
    }

    @Override
    @Transactional
    public List<Person> getPersonsWithWeightLessThan(double weight) {
        String jpql = "SELECT e FROM Person e WHERE e.weight < :weight";
        return entityManager.createQuery(jpql)
                .setParameter("weight", weight)
                .getResultList();
    }


}
