package itmo.soa.demography.resource;

import itmo.soa.demography.dto.MessageWrapper;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionHandler implements ExceptionMapper<NotFoundException> {

    @Context
    private HttpHeaders headers;

    public Response toResponse(NotFoundException ex){
        return Response.status(404)
                .entity(new MessageWrapper("страница не найдена"))
                .type(MediaType.APPLICATION_JSON_TYPE).build();
    }

//    private String getAcceptType(){
//        List<MediaType> accepts = headers.getAcceptableMediaTypes();
//        if (accepts!=null && accepts.size() > 0) {
//            //choose one
//        }else {
//            //return a default one like Application/json
//        }



    }