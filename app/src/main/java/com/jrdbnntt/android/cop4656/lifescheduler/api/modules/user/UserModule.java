package com.jrdbnntt.android.cop4656.lifescheduler.api.modules.user;

import com.android.volley.Response;
import com.jrdbnntt.android.cop4656.lifescheduler.api.LifeSchedulerApi;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.user.data.LoginRequest;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.user.data.RegisterRequest;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.user.data.RegisterResponse;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.user.data.get.SummaryResponse;
import com.jrdbnntt.android.std.api.ApiModule;
import com.jrdbnntt.android.std.api.data.EmptyResponse;

/**
 * User related routes
 */

public class UserModule extends ApiModule<LifeSchedulerApi> {

    public UserModule(LifeSchedulerApi api) {
        super(api);
    }

    public void register(
        RegisterRequest req,
        Response.Listener<RegisterResponse> res,
        Response.ErrorListener err
    ) {
        api.sendPost("/user/register", req, RegisterResponse.class, null, res, err);
    }

    public void logIn(
            LoginRequest req,
            Response.Listener<EmptyResponse> res,
            Response.ErrorListener err
    ) {
        api.sendPost("/user/login", req, EmptyResponse.class, null, res, err);
    }

    public void logOut(
            Response.Listener<EmptyResponse> res,
            Response.ErrorListener err
    ) {
        api.sendGet("/user/logout", EmptyResponse.class, null, res, err);
    }

    public void getSummary(
            Response.Listener<SummaryResponse> res,
            Response.ErrorListener err
    ) {
        api.sendGet("/user/get/summary", SummaryResponse.class, null, res, err);
    }
}
