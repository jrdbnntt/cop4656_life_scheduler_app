package com.jrdbnntt.android.std.api.data;

/**
 * Standard error response from server
 */

public class ErrorResponse extends GsonObject {
    public String error;    // Type of exception: External/Internal
    public String cause;    // Actual exception type thrown
    public String message;
}
