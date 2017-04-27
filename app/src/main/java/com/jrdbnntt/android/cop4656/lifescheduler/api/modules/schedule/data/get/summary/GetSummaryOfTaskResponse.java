package com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.get.summary;

import android.support.annotation.Nullable;

import com.jrdbnntt.android.std.api.data.GsonResponse;

/**
 * TODO
 */
public class GetSummaryOfTaskResponse extends GsonResponse {
    public String title;
    public Boolean completed;
    public Integer priority;
    @Nullable public Boolean completion_assumed;
}
