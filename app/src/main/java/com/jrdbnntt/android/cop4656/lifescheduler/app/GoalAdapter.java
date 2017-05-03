package com.jrdbnntt.android.cop4656.lifescheduler.app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.jrdbnntt.android.cop4656.lifescheduler.R;
import com.jrdbnntt.android.cop4656.lifescheduler.api.LifeSchedulerApi;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.get.GetGoalRequirementsRequest;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.get.GetGoalRequirementsResponse;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.get.summary.GetSummaryOfGoalRequest;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.get.summary.GetSummaryOfGoalResponse;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * TODO
 */

public class GoalAdapter extends ArrayAdapter<Integer> {
    private LifeSchedulerApi api;
    private Integer parentGoalId;

    public GoalAdapter(@NonNull Context context, ArrayList<Integer> histories,
                       LifeSchedulerApi api, Integer parentGoalId) {
        super(context, 0, histories);
        this.api = api;
        this.parentGoalId = parentGoalId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Integer goalId = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_goal_summary, parent, false);
        }

        final TextView tvGoalTitle = (TextView) convertView.findViewById(R.id.tvGoalTitle);
        tvGoalTitle.setText(" ");

        GetSummaryOfGoalRequest req = new GetSummaryOfGoalRequest();
        req.goal_id = goalId;
        api.getScheduleModule().getSummaryOfGoal(req, new Response.Listener<GetSummaryOfGoalResponse>() {
            @Override
            public void onResponse(GetSummaryOfGoalResponse response) {
                tvGoalTitle.setText(response.title);
            }
        }, api.dialogErrorListener(getContext()));

        return convertView;
    }

    public void refresh() {
        GetGoalRequirementsRequest req = new GetGoalRequirementsRequest();
        req.parent_goal_id = parentGoalId;
        api.getScheduleModule().getGoalRequirements(req, new Response.Listener<GetGoalRequirementsResponse>() {
            @Override
            public void onResponse(GetGoalRequirementsResponse response) {
                clear();

                if (response.goal_ids != null) {
                    addAll(new ArrayList<>(Arrays.asList(response.goal_ids)));

                } else {
                    Toast.makeText(getContext(), "You have no goals, create one!", Toast.LENGTH_LONG).show();
                }

                Log.v("Goals", "updated, " + getCount() + " total");
            }
        }, api.dialogErrorListener(getContext()));
    }

}