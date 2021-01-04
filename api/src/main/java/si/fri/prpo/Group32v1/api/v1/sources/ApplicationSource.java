package si.fri.prpo.Group32v1.api.v1.sources;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.Group32v1.entities.Application;
import si.fri.prpo.Group32v1.entities.Application;
import si.fri.prpo.Group32v1.services.beans.ApplicationBean;
import si.fri.prpo.Group32v1.services.controllers.ApplicationControllerBean;
import si.fri.prpo.Group32v1.services.dtos.ApplicationDto;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("applications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, OPTIONS")
public class ApplicationSource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private ApplicationBean applicationBean;

    @Inject
    private ApplicationControllerBean applicationControllerBean;

    @Operation(description = "Get application", summary = "Get applications")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Applications fetched successfully"
            )
    })
    //@RolesAllowed("admin")
    @GET
    public Response getApplications() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long appsCount = applicationBean.getApplicationsCount(query);

        return Response
                .ok(applicationBean.getApplications(query))
                .header("X-Total-Count", appsCount)
                .build();
    }

    @Operation(description = "Get application", summary = "Get application with id")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Applications fetched successfully"
            ),
            @APIResponse(responseCode = "405",
                    description = "Validation error"
            )
    })
    //@RolesAllowed("admin")
    @GET
    @Path("{id}")
    public Response getApplicationById(@PathParam("id") Integer id) {
        Application app = applicationBean.getApplicationById(id);

        if(app == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        else {
            return Response.ok(app).build();
        }
    }

    @Operation(description = "Create application", summary = "Create new application")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Application created successfully"
            ),
            @APIResponse(responseCode = "405",
                    description = "Validation error"
            )
    })
    //@RolesAllowed("admin")
    @POST
    public Response addApplication(@RequestBody(
            description = "DTO object for Application creation.",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Application.class)))
                                               ApplicationDto applicationDto) {

        return Response.status(Response.Status.CREATED).entity(applicationControllerBean.createApplication(applicationDto)).build();
    }

    @Operation(description = "Update application", summary = "Update application with id")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Application with id updated successfully"
            ),
            @APIResponse(responseCode = "405",
                    description = "Validation error"
            )
    })
    @RolesAllowed("admin")
    @PUT
    @Path("{id}")
    public Response updateApplication(@PathParam("id") Integer id, Application application) {
        return Response.status(Response.Status.OK).entity(applicationBean.updateApplication(id, application)).build();
    }

    @Operation(description = "Delete application", summary = "Delete application with id")
    @APIResponses({
            @APIResponse(responseCode = "204",
                    description = "Application with id deleted successfully"
            ),
            @APIResponse(responseCode = "405",
                    description = "Validation error"
            )
    })
    //@RolesAllowed("admin")
    @DELETE
    @Path("{id}")
    public Response removeApplication(@PathParam("id") Integer id) {
        return Response.status(Response.Status.OK).entity(applicationBean.removeApplication(id)).build();
    }
}
