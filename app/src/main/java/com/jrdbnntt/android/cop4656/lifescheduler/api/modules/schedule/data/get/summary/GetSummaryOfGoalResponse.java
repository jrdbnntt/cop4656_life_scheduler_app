package com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.get.summary;

import com.jrdbnntt.android.std.api.data.GsonResponse;

/**
 * TODO
 */
public class GetSummaryOfGoalResponse extends GsonResponse {
    public String title;
    public Boolean completed;
    public Integer total_child_tasks;
    public Integer total_child_tasks_completed;
}
