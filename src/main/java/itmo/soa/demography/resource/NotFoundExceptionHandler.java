package itmo.soa.demography.resource;

import itmo.soa.demography.dto.MessageWrapper;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionHandler implements ExceptionMapper<NotFoundException> {

    public Response toResponse(NotFoundException ex) {

        return Response.status(404)
                .entity(new MessageWrapper(ex.getMessage()))
                .type(MediaType.APPLICATION_JSON_TYPE).build();
    }

}