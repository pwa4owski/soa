package itmo.soa.demography.resource;

import itmo.soa.demography.dto.CountryErrorWrapper;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class IllegalArgumentExceptionHandler implements ExceptionMapper<IllegalArgumentException> {
    @Override
    public Response toResponse(IllegalArgumentException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(CountryErrorWrapper.builder()
                        .nationality(e.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
