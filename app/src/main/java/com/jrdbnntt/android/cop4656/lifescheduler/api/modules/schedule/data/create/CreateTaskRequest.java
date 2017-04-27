package com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.create;

import com.jrdbnntt.android.std.api.data.GsonRequest;

/**
 * TODO
 */
public class CreateTaskRequest extends GsonRequest {
    public Integer parent_goal_id;
    public Integer task_type;
    public String title;
    public Integer priority;
    public Integer total_time_required_m;

    // Optional
    public Boolean completion_verification_required;
    public String description;
    public String location;
    public String fixed_time_allotment_start;
    public String fixed_time_allotment_end;

}
