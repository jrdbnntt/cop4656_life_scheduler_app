package com.jrdbnntt.android.std.api;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.jrdbnntt.android.std.api.data.ErrorResponse;
import com.jrdbnntt.android.std.api.data.GsonObject;
import com.jrdbnntt.android.std.api.data.GsonRequest;
import com.jrdbnntt.android.std.api.data.GsonResponse;

import java.util.Map;

/**
 * A general API class which combines Gson & Volley for GET/POST requests
 */

public abstract class GsonVolleyApi {

    private String baseUrl;
    private Context context;

    public GsonVolleyApi(String baseUrl, Context context) {
        this.baseUrl = baseUrl;
        this.context = context;
    }

    public <Res extends GsonObject> void sendGet(
            String localUrl,
            Class<Res> responseClass,
            Map<String, String> headers,
            Response.Listener<Res> responseListener,
            Response.ErrorListener errorListener
    ) {
        String url = baseUrl + localUrl;
        GsonVolleyGetRequest<Res> request = new GsonVolleyGetRequest<>(
                url, responseClass, headers, responseListener, errorListener
        );
        VolleyManager.getInstance(context).addToRequestQueue(request);
    }

    public <Res extends GsonResponse> void sendGet(
            String localUrl,
            Class<Res> responseClass,
            Response.Listener<Res> responseListener,
            Response.ErrorListener errorListener
    ) {
        sendGet(localUrl, responseClass, null, responseListener,errorListener);
    }

    public <Req extends GsonRequest, Res extends GsonResponse> void sendPost(
            String localUrl,
            Req requestObject,
            Class<Res> responseClass,
            Map<String, String> headers,
            Response.Listener<Res> responseListener,
            Response.ErrorListener errorListener
    ) {
        String url = baseUrl + localUrl;
        GsonVolleyPostRequest<Req, Res> request = new GsonVolleyPostRequest<>(
                url, requestObject, responseClass, headers, responseListener, errorListener
        );
        VolleyManager.getInstance(context).addToRequestQueue(request);
    }

    public <Req extends GsonRequest, Res extends GsonResponse> void sendPost(
            String localUrl,
            String requestJson,
            Class<Res> responseClass,
            Map<String, String> headers,
            Response.Listener<Res> responseListener,
            Response.ErrorListener errorListener
    ) {
        String url = baseUrl + localUrl;
        GsonVolleyPostRequest<Req, Res> request = new GsonVolleyPostRequest<>(
                url, requestJson, responseClass, headers, responseListener, errorListener
        );
        VolleyManager.getInstance(context).addToRequestQueue(request);
    }

    public <Req extends GsonRequest, Res extends GsonResponse> void sendPost(
            String localUrl,
            Req requestObject,
            Class<Res> responseClass,
            Response.Listener<Res> responseListener,
            Response.ErrorListener errorListener
    ) {
        sendPost(localUrl, requestObject, responseClass, null, responseListener, errorListener);
    }

    public Response.ErrorListener dialogErrorListener(final Context context) {
        return new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                buildErrorDialog(context, error).show();
            }

        };
    }

    public AlertDialog buildErrorDialog(Context context, VolleyError error) {
        Log.e(this.getClass().getName(), error.getMessage());

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setPositiveButton("Ok", null);

        // Build the dialog based on the response
        NetworkResponse response = getNetworkResponse(error);
        if (response == null) {
            dialogBuilder.setTitle("Error");
            dialogBuilder.setMessage("Network error");
        } else {
            switch (response.statusCode) {
                case 400:   // Bad request (validation probably failed)
                    Gson gson = new Gson();
                    ErrorResponse res = gson.fromJson(
                            error.getMessage(), ErrorResponse.class);
                    dialogBuilder.setTitle(res.cause);
                    dialogBuilder.setMessage(res.message);
                    break;
                case 401:   // Unauthorized, possibly not logged in
                    dialogBuilder.setTitle("Unauthorized");
                    dialogBuilder.setMessage("You do not have permission to do that");
                    break;
                case 404:
                    dialogBuilder.setTitle("Page Not Found");
                    dialogBuilder.setMessage("API route path incorrect");
                default:
                    dialogBuilder.setTitle("Error " + response.statusCode);
                    dialogBuilder.setMessage(error.getMessage());
            }
        }

        return dialogBuilder.create();
    }

    public NetworkResponse getNetworkResponse(VolleyError error) {
        if (error.networkResponse != null) {
            return error.networkResponse;
        }
        Throwable cause = error.getCause();
        if (cause != null && cause instanceof VolleyError) {
            return ((VolleyError) cause).networkResponse;
        }
        return null;
    }
}
