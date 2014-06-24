package org.resthub.identity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception mapped to Conflict HTTP status code (409)
 */
@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.CONFLICT)
public class ExpectationFailedException extends RuntimeException {

    public ExpectationFailedException() {
        super();
    }

    public ExpectationFailedException(final String message, final Throwable cause) {
        super(message + " -> " + cause.getMessage(), cause);
    }

    public ExpectationFailedException(final String message) {
        super(message);
    }

    public ExpectationFailedException(final Throwable cause) {
        super(cause);
    }
}