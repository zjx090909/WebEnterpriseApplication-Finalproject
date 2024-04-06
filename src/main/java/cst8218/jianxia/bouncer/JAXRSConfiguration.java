package cst8218.jianxia.bouncer;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.PasswordHash;

/**
 * Configures JAX-RS for the application.
 * @author Jianxia
 */
@ApplicationPath("resources")
@BasicAuthenticationMechanismDefinition
@DatabaseIdentityStoreDefinition(
dataSourceLookup = "${'java:comp/DefaultDataSource'}",
callerQuery = "#{'select password from app.appuser where userId = ?'}",
groupsQuery = "select groupid from app.appuser where userId = ?",
hashAlgorithm = PasswordHash.class,
priority = 10
)

@Named
@ApplicationScoped
public class JAXRSConfiguration extends Application {
    
}
