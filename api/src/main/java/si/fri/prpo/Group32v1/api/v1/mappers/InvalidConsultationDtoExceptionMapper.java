package si.fri.prpo.Group32v1.api.v1.mappers;

import si.fri.prpo.Group32v1.services.exceptions.InvalidConsultationDtoException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidConsultationDtoExceptionMapper implements ExceptionMapper<InvalidConsultationDtoException> {
    @Override
    public Response toResponse(InvalidConsultationDtoException e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("{\"error\":\"" + e.getMessage() + "\"}")
                .build();
    }
}
