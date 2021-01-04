package si.fri.prpo.Group32v1.api.v1;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

//@DeclareRoles({"user", "admin"})
@OpenAPIDefinition(info = @Info(title = "Consultations API", version = "v1",
        license = @License(name = "dev"), description = "API for Consultations application."),
        servers = @Server(url = "http://localhost:8080"))

@ApplicationPath("v1")
@CrossOrigin(supportedMethods = "POST, GET, HEAD, DELETE, OPTIONS")
public class ConsultationsApplication extends Application {
}
