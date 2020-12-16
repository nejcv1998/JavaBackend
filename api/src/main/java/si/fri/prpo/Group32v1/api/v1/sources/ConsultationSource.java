package si.fri.prpo.Group32v1.api.v1.sources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.Group32v1.entities.Channel;
import si.fri.prpo.Group32v1.entities.Consultation;
import si.fri.prpo.Group32v1.services.beans.ConsultationBean;
import si.fri.prpo.Group32v1.services.controllers.ConsultationControllerBean;
import si.fri.prpo.Group32v1.services.dtos.ConsultationDto;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("consultations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ConsultationSource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private ConsultationBean consultationBean;

    @Inject
    private ConsultationControllerBean consultationControllerBean;

    @Operation(description = "Get consultation", summary = "Get consultations")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Consultations fetched successfully"
            )
    })
    @RolesAllowed("admin")
    @GET
    public Response getConsultations() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long consCount = consultationBean.getConsultationsCount(query);

        return Response
                .ok(consultationBean.getConsultations(query))
                .header("X-Total-Count", consCount)
                .build();
    }

    @Operation(description = "Get consultation", summary = "Get consultation with id")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Consultations fetched successfully"
            ),
            @APIResponse(responseCode = "405",
                    description = "Validation error"
            )
    })
    @RolesAllowed("admin")
    @GET
    @Path("{id}")
    public Response getConsultationById(@PathParam("id") Integer id) {
        Consultation consultation = consultationBean.getConsultationById(id);

        if (consultation == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        else {
            return Response.ok(consultation).build();
        }
    }

    @Operation(description = "Create consultation", summary = "Create new consultation")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Consultation created successfully"
            ),
            @APIResponse(responseCode = "405",
                    description = "Validation error"
            )
    })
    @RolesAllowed("admin")
    @POST
    public Response addConsultation(@RequestBody(
            description = "DTO object for Consultation creation.",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Consultation.class)))
                                                ConsultationDto consultationDto) {

        return Response.status(Response.Status.CREATED).entity(consultationControllerBean.createConsultation(consultationDto)).build();
    }

    @Operation(description = "Update consultation", summary = "Update consultation with id")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Consultation with id updated successfully"
            ),
            @APIResponse(responseCode = "405",
                    description = "Validation error"
            )
    })
    @RolesAllowed("admin")
    @PUT
    @Path("{id}")
    public Response updateConsultation(@PathParam("id") Integer id, Consultation consultation) {
        return Response.status(Response.Status.OK).entity(consultationBean.updateConsultation(id, consultation)).build();
    }

    @Operation(description = "Delete consultation", summary = "Delete consultation with id")
    @APIResponses({
            @APIResponse(responseCode = "204",
                    description = "Consultation with id deleted successfully"
            ),
            @APIResponse(responseCode = "405",
                    description = "Validation error"
            )
    })
    @RolesAllowed("admin")
    @DELETE
    @Path("{id}")
    public Response removeConsultation(@PathParam("id") Integer id) {
        return Response.status(Response.Status.OK).entity(consultationBean.removeConsultation(id)).build();
    }

    @GET
    @Path("channels")
    public Response getChannels() {
        List<Channel> ch = consultationBean.getChannels();

        return Response
                .ok(ch)
                .build();
    }
}
