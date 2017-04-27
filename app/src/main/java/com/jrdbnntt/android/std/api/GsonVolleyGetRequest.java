package com.jrdbnntt.android.std.api;

import com.android.volley.Response;

import java.util.Map;

public class GsonVolleyGetRequest<ResT> extends GsonVolleyRequest<ResT> {

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param method HTTP method (Request.Method)
     * @param url URL of the request to make
     * @param responseClass Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     */
    public GsonVolleyGetRequest(String url, Class<ResT> responseClass, Map<String, String> headers,
                                Response.Listener<ResT> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, responseClass, headers, listener, errorListener);
    }

}