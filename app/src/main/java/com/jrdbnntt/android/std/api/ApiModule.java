package com.jrdbnntt.android.std.api;

/**
 * Extensions of the main API, consisting of functions that initiate requests
 */

public abstract class ApiModule<ApiT extends GsonVolleyApi> {
    protected ApiT api;

    public ApiModule(ApiT api) {
        this.api = api;
    }
}
