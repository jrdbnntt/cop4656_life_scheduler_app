package com.jrdbnntt.android.cop4656.lifescheduler.api;

import android.content.Context;

import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.ScheduleModule;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.user.UserModule;
import com.jrdbnntt.android.std.api.GsonVolleyApi;


/**
 * Api for Life Scheduler
 */
public class LifeSchedulerApi extends GsonVolleyApi {

    private static final String BASE_URL = "https://lifescheduler.jrdbnntt.com/api";

    private UserModule userModule;
    private ScheduleModule scheduleModule;

    public LifeSchedulerApi(Context context) {
        super(BASE_URL, context);
    }


    public UserModule getUserModule() {
        if (userModule == null) {
            userModule = new UserModule(this);
        }
        return userModule;
    }

    public ScheduleModule getScheduleModule() {
        if (scheduleModule == null) {
            scheduleModule = new ScheduleModule(this);
        }
        return scheduleModule;
    }


}
