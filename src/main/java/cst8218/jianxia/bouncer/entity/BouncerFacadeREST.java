package cst8218.jianxia.bouncer.entity;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.WebApplicationException;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;

/**
 * BouncerFacadeREST provides a RESTful web service interface for performing CRUD operations on Bouncer entities.
 * 
 * This class handles HTTP requests related to Bouncers and delegates the business logic to the appropriate methods in the superclass AbstractFacade.
 * Instances of this class are stateless session beans, which allows them to handle multiple concurrent requests from clients efficiently.
 * 
 * POST on the root resource (all bouncers):if id is null, it will create a new bouncer with all the non-id values initialized to reasonable defaults; 
 * if id is not null, throw an error to indicate it is a bad request.
 * 
 * POST on a specific id will update the Bouncer having that id with the new non- null information given by the Bouncer in the body of the request.
 * 
 * @author zhangjianxia
 */
@Stateless
@Path("cst8218.jianxia.bouncer.entity.bouncer")
@DeclareRoles("Admin")
public class BouncerFacadeREST extends AbstractFacade<Bouncer> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;
    
    @EJB
    private BouncerFacade bouncerFacade; // Inject BouncerFacade

    public BouncerFacadeREST() {
        super(Bouncer.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @RolesAllowed({"RestGroup", "Admin"})
    public Bouncer create(Bouncer entity) {
    if (entity.getId() != null) {
        // If the id is not null, it is a bad request
        throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).build());
    }
    // Initialize any non-id null values to reasonable defaults
    super.create(entity);
    // Return the created Bouncer
    return entity;
}
    
    @POST
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @RolesAllowed({"RestGroup", "Admin"})
    public Response updateBouncer(@PathParam("id") Long id, Bouncer entity) {
    
    // if the entity not been exist or id does not exist, it is a bad request
    if (entity == null || !id.equals(entity.getId())) {
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    Bouncer existingBouncer = super.find(id);
    // if the entity not been found, throw a not found message
    if (existingBouncer == null) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    // Update non-null attributes of the existing Bouncer with the corresponding values from the new Bouncer
    if (entity.getX() != 0) {
        existingBouncer.setX(entity.getX());
    }
    if (entity.getY() != 0) {
        existingBouncer.setY(entity.getY());
    }
    super.edit(existingBouncer);
    return Response.status(Response.Status.NO_CONTENT).build();
}

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @RolesAllowed({"RestGroup", "Admin"})
    public void edit(@PathParam("id") Long id, Bouncer entity) {
        if (!id.equals(entity.getId())) {
            // If the Bouncer in the body of the request has a non-matching non-null id, it is a bad request
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).build());
        }
        if (super.find(id) == null) {
            // If the id doesn’t already exist in the database, returns an appropriate error response
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
        }
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    @RolesAllowed({"RestGroup", "Admin"})
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @RolesAllowed({"RestGroup", "Admin"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Bouncer find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @RolesAllowed({"RestGroup", "Admin"})
    public List<Bouncer> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @RolesAllowed({"RestGroup", "Admin"})
    public List<Bouncer> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed({"RestGroup", "Admin"})
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }   
}
