package dk.mgarde.wishlist.model;

import lombok.Data;

/**
 * Response object for use in case of errors or successful queries.
 */
@Data
public class RestResponse {

    public enum Status {
        FAILED, SUCCESS
    }

    private Status status;
    private String message;

    public RestResponse(Status status, String message) {
        setStatus(status);
        setMessage(message);
    }

}