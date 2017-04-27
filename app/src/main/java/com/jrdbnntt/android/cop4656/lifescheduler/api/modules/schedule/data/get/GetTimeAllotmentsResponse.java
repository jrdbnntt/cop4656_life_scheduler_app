package com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.get;

import com.jrdbnntt.android.std.api.data.GsonResponse;

/**
 * TODO
 */

public class GetTimeAllotmentsResponse extends GsonResponse {
    public TimeAllotmentSummary[] time_allotments;

    public static class TimeAllotmentSummary {
        public Integer task_id;
        public String task_title;
        public String start_time;
        public String end_time;
        public Integer duration;
    }
}
