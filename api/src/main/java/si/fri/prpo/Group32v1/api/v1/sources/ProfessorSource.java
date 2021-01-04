package si.fri.prpo.Group32v1.api.v1.sources;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.Group32v1.entities.Professor;
import si.fri.prpo.Group32v1.services.beans.ProfessorBean;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("professors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, OPTIONS")
public class ProfessorSource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private ProfessorBean professorBean;

    @Operation(description = "Get professor", summary = "Get professors")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Professors fetched successfully"
            )
    })
    @RolesAllowed("admin")
    @GET
    public Response getProfessors() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long profCount = professorBean.getProfessorsCount(query);

        return Response
                .ok(professorBean.getProfessors(query))
                .header("X-Total-Count", profCount)
                .build();
    }

    @Operation(description = "Get professor", summary = "Get professor with id")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Professors fetched successfully"
            ),
            @APIResponse(responseCode = "405",
                    description = "Validation error"
            )
    })
    @RolesAllowed("admin")
    @GET
    @Path("{id}")
    public Response getProfessorById(@PathParam("id") Integer id) {
        Professor prof = professorBean.getProfessorById(id);

        if(prof == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        else {
            return Response.ok(prof).build();
        }
    }

    @Operation(description = "Create professor", summary = "Create new professor")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Professor created successfully"
            ),
            @APIResponse(responseCode = "405",
                    description = "Validation error"
            )
    })
    @RolesAllowed("admin")
    @POST
    public Response addProfessor(@RequestBody(
            description = "DTO object for Professor creation.",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Professor.class)))
                                             Professor professor) {

        return Response.status(Response.Status.CREATED).entity(professorBean.addProfessor(professor)).build();
    }

    @Operation(description = "Update professor", summary = "Update professor with id")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Professor with id updated successfully"
            ),
            @APIResponse(responseCode = "405",
                    description = "Validation error"
            )
    })
    @RolesAllowed("admin")
    @PUT
    @Path("{id}")
    public Response updateProfessor(@PathParam("id") Integer id, Professor professor) {
        return Response.status(Response.Status.OK).entity(professorBean.updateProfessor(id, professor)).build();
    }

    @Operation(description = "Delete professor", summary = "Delete professor with id")
    @APIResponses({
            @APIResponse(responseCode = "204",
                    description = "Professor with id deleted successfully"
            ),
            @APIResponse(responseCode = "405",
                    description = "Validation error"
            )
    })
    @RolesAllowed("admin")
    @DELETE
    @Path("{id}")
    public Response removeProfessor(@PathParam("id") Integer id) {
        return Response.status(Response.Status.OK).entity(professorBean.removeProfessor(id)).build();
    }
}
