package dk.mgarde.wishlist.rest;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
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

import dk.mgarde.wishlist.model.RestResponse;
import dk.mgarde.wishlist.model.RestResponse.Status;
import dk.mgarde.wishlist.model.Wish;

/**
 * Class for handling {@link Wish} related endpoints.
 */
@ApplicationScoped
@Transactional
@Path("/wish")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WishResource {

    /**
     * Get the wishlist for a given {@link dk.mgarde.wishlist.model.Person}.
     */
    @GET
    @Path("/list")
    public List<Wish> getWishListByPersonId(@QueryParam("personId") final long personId) {
        try {
            return Wish.getWishListByPersonId(personId);
        } catch (Exception e) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                    .entity(new RestResponse(RestResponse.Status.FAILED, e.getMessage())).build());
        }
    }

    /**
     * Adds a provided {@link Wish} to an existing person.
     */
    @POST
    public RestResponse addWishToPersonById(@QueryParam("personId") final long personId, Wish wish) {
        try {
            Wish.addWishToPersonById(personId, wish);
            return new RestResponse(Status.SUCCESS, "Wish added.");
        } catch (Exception e) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                    .entity(new RestResponse(RestResponse.Status.FAILED, e.getMessage())).build());
        }

    }

    /**
     * Deletes a {@link Wish}. Person/Wish relation is handled properly
     */
    @DELETE
    public RestResponse deleteWish(@QueryParam("wishId") final long wishId) {
        try {
            Wish.deleteWish(wishId);
            return new RestResponse(Status.SUCCESS, "Wish deleted");
        } catch (Exception e) {
            return new RestResponse(Status.FAILED, e.getMessage());
        }
    }

}