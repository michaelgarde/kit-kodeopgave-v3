package dk.mgarde.wishlist.rest;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import dk.mgarde.wishlist.model.Person;
import dk.mgarde.wishlist.model.RestResponse;

/**
 * Class for handling {@link Person} related endpoints.
 */
@ApplicationScoped
@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {

    private static final Logger log = Logger.getLogger(PersonResource.class);

    /**
     * {@link GET} endpoint: Gets a list of all persons in the database
     */
    @GET
    public List<Person> getAllPersons() {
        log.info("Getting all persons.");
        try {
            return Person.getAllPersonsList();
        } catch (Exception e) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new RestResponse(RestResponse.Status.FAILED, e.getMessage())).build());
        }
    }

    /**
     * {@link GET} endpoint: Gets the first {@link Person} with the id defined in
     * the query parameter 'personId'
     */
    @GET
    @Path("/search")
    public Person getPersonById(@QueryParam("personId") long personId) {
        log.info("Getting person by personId " + personId);
        try {
            return Person.findPersonBy(personId);
        } catch (Exception e) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new RestResponse(RestResponse.Status.FAILED, e.getMessage())).build());
        }
    }

    /**
     * {@link GET} endpoint: Gets the first {@link Person} with the name defined in
     * the query parameter 'name'
     */
    @GET
    @Path("/search")
    public List<Person> getPersonByName(@QueryParam("name") String searchParam) {
        log.info("Getting by name: " + searchParam);
        try {
            return Person.findPersonBy(searchParam);
        } catch (Exception e) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new RestResponse(RestResponse.Status.FAILED, e.getMessage())).build());
        }
    }

    /**
     * {@link POST} endpoint: Adds a {@link Person}, if a person with the name and
     * birthday in the request body doesn't already exist.
     */
    @POST
    public Person add(Person person) {
        log.info("Adding person: " + person);
        try {
            return Person.addNewPerson(person);
        } catch (Exception e) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                    .entity(new RestResponse(RestResponse.Status.FAILED, e.getMessage())).build());
        }
    }

    /**
     * {@link DELETE} endpoint: Deletes a {@link Person} with id
     */
    @DELETE
    public RestResponse delete(@QueryParam("personId") long personId) {
        log.info("Deleting person with id: " + personId);
        try {
            Person.deletePerson(personId);
            return new RestResponse(RestResponse.Status.SUCCESS, "Person with id; " + personId + " deleted.");
        } catch (Exception e) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                    .entity(new RestResponse(RestResponse.Status.FAILED, e.getMessage())).build());
        }
    }
}