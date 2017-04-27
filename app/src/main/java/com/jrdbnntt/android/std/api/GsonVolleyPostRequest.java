package com.jrdbnntt.android.std.api;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class GsonVolleyPostRequest<ReqT, ResT> extends GsonVolleyRequest<ResT> {

    private final byte[] requestBody;

    /**
     * Make a request with a JSON request body and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     * @param requestObject The object to be converted into Json
     * @param responseClass Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     */
    public GsonVolleyPostRequest(String url, ReqT requestObject, Class<ResT> responseClass,
                                 Map<String, String> headers, Response.Listener<ResT> listener,
                                 Response.ErrorListener errorListener) {
        super(Method.POST, url, responseClass, headers, listener, errorListener);

        this.requestBody = gson.toJson(requestObject).getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Make a request with a JSON request body and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     * @param requestJson JSON String of an already Gson parsed object (for non-basic req obj)
     * @param responseClass Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     */
    public GsonVolleyPostRequest(String url, String requestJson, Class<ResT> responseClass,
                                 Map<String, String> headers, Response.Listener<ResT> listener,
                                 Response.ErrorListener errorListener) {
        super(Method.POST, url, responseClass, headers, listener, errorListener);
        this.requestBody = requestJson.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String getBodyContentType() {
        return "application/json; charset=" + StandardCharsets.UTF_8;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return requestBody;
    }

}