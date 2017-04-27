package com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.create;

import com.jrdbnntt.android.std.api.data.GsonRequest;

/**
 * TODO
 */
public class CreateGoalRequest extends GsonRequest {
    public String title;

    // Optional
    public String description;
    public String default_location;
    public Integer default_priority;
    public Integer parent_goal_id;
}
