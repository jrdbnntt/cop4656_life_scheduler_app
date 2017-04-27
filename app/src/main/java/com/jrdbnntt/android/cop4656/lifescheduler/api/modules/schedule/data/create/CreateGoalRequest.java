package com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.create;

import com.jrdbnntt.android.std.api.data.GsonRequest;

/**
 * TODO
 */
public class CreateGoalRequest extends GsonRequest {
    String title;
    String description;
    String default_location;
    Integer default_priority;
    Integer parent_goal_id;
}
