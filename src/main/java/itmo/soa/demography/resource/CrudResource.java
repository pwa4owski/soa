package itmo.soa.demography.resource;

import itmo.soa.demography.dto.LocationDto;
import itmo.soa.demography.dto.PersonDto;
import itmo.soa.demography.model.Person;
import itmo.soa.demography.sevice.PersonsService;
import itmo.soa.demography.util.Page;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/persons")
public class CrudResource {

    @Inject
    PersonsService service;

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPersons() {
        return Response.ok().entity(service.getAll()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersons(
            @QueryParam("filter") List<String> filters,
            @QueryParam("sort") List<String> sorts,
            @QueryParam("page") @Pattern(regexp = "^0|[1-9]+\\d*$", message = "page must be not negative integer")
                @DefaultValue("0") String pageParam,
            @QueryParam("size") @Pattern(regexp = "[1-9]+\\d*$", message = "size must be positive integer")
                @DefaultValue("25") String sizeParam
    ) {
        Page res;
        try {
            int page = Integer.parseInt(pageParam);
            int size = Integer.parseInt(sizeParam);
            if(page < 0 || size <= 0) {
                throw new ValidationException();
            }
            res = service.getPage(filters, sorts, page, size);
        } catch (NumberFormatException|ValidationException e){
            res = service.getPage(filters, sorts, 0, 25);
        }
        return Response.ok().entity(res).build();
    }

    @GET
    @Path("weight-less/{weight}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonsWeightLess(@PathParam("weight") @Min(value = 1, message = "Вес должен быть положительным числом") Double weight) {
        List<Person> personList = service.getPersonsWithWeightLessThan(weight);
        return Response.ok().entity(personList).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPerson(@Valid PersonDto personDto){
        Person person = service.addPerson(personDto);
        return Response.ok().entity(person).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson(@PathParam("id") long id) {
        Person person = service.getById(id);
        return Response.status(200).entity(person).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePerson(@PathParam("id") long id, @Valid PersonDto personDto) {
        Person person = service.updatePersonById(id, personDto);
        return Response.status(200).entity(person).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePerson(@PathParam("id") long id) {
        service.deletePerson(id);
        return Response.status(204).build();
    }

    @DELETE
    @Path("/weight/{weight}")
    public Response deleteByWeight(@PathParam("weight") @Min(value = 1) Double weight) {
        service.deletePersonsWithLessWeight(weight);
        return Response.status(204).build();
    }

    @PUT //put вместо delete, поскольку последний не хочет принимать тело запроса
    @Path("/location")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteByLocation(@Valid @NotNull LocationDto locationDto) {
        service.deletePersonsByLocation(locationDto);
        return Response.status(204).build();
    }




}
