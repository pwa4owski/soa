package itmo.soa.demography.resource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Provider
public class MyExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(final ConstraintViolationException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(prepareMessage(exception))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    private Map<String, String> prepareMessage(ConstraintViolationException exception) {
        Map<String, String> errors = new HashMap<>();
        int cnt = 0;
        for (ConstraintViolation<?> cv : exception.getConstraintViolations()) {
            try {
                errors.put(Arrays.stream(cv.getPropertyPath().toString()
                        .split("\\.arg\\d\\.")).skip(1).findFirst().get(), cv.getMessage());
            }
            catch (Exception e) {
                errors.put("message" + cnt++, cv.getMessage());
            }
        }
        return errors;
    }
}