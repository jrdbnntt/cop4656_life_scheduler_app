package com.jrdbnntt.android.cop4656.lifescheduler.app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.Response;
import com.jrdbnntt.android.cop4656.lifescheduler.R;
import com.jrdbnntt.android.cop4656.lifescheduler.api.LifeSchedulerApi;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.get.summary.GetSummaryOfTaskRequest;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.get.summary.GetSummaryOfTaskResponse;

import java.util.ArrayList;

/**
 * TODO
 */

public class TaskAdapter extends ArrayAdapter<Integer> {
    private LifeSchedulerApi api;

    public TaskAdapter(@NonNull Context context, ArrayList<Integer> histories,
                       LifeSchedulerApi api) {
        super(context, 0, histories);
        this.api = api;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Integer taskId = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_goal_summary, parent, false);
        }

        final TextView tvTitle = (TextView) convertView.findViewById(R.id.tvGoalTitle);
        tvTitle.setText(" ");

        GetSummaryOfTaskRequest req = new GetSummaryOfTaskRequest();
        req.task_id = taskId;
        api.getScheduleModule().getSummaryOfTask(req, new Response.Listener<GetSummaryOfTaskResponse>() {
            @Override
            public void onResponse(GetSummaryOfTaskResponse response) {
                tvTitle.setText(response.title);
            }
        }, api.dialogErrorListener(getContext()));

        return convertView;
    }
}
