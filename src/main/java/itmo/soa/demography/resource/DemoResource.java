package itmo.soa.demography.resource;

import itmo.soa.demography.dto.Person;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/persons")
public class DemoResource {

    private static final List<Person> persons;

    static {
        persons = new ArrayList<>();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersons() {
        return Response.status(200).entity(persons).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPerson(@Valid Person person){
        persons.add(person);
        return Response.status(201).entity(persons).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson(@PathParam("id") long id) {
        Person person = persons.stream().filter(p -> p.getId().equals(id)).findFirst()
                .orElseThrow(() -> new NotFoundException("нет элемента с id " + id));
        return Response.status(204).entity(person).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePerson(@PathParam("id") long id, @Valid Person person) {
        Person personToUpdate = persons.stream().filter(p -> p.getId().equals(id)).findFirst()
                .orElseThrow(() -> new NotFoundException("нет элемента с id " + id));
        personToUpdate.setName(person.getName());
        personToUpdate.setWeight(person.getWeight());
        personToUpdate.setCoordinates(person.getCoordinates());
        return Response.status(200).entity(personToUpdate).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePerson(@PathParam("id") long id) {
        Person deletedPerson = persons.stream().filter(p -> p.getId().equals(id)).findFirst()
                .orElseThrow(() -> new NotFoundException("нет элемента с id " + id));
        persons.remove(deletedPerson);
        return Response.status(204).entity(deletedPerson).build();
    }
}