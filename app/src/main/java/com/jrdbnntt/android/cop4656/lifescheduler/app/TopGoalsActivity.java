package com.jrdbnntt.android.cop4656.lifescheduler.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.util.Locale;

public class TopGoalsActivity extends AppCompatActivity {

    LifeSchedulerApi api;
    ArrayList<Integer> goalIds = new ArrayList<>();
    ListView lvGoals;
    GoalAdapter goalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_goals);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }

    public void init() {
        api = new LifeSchedulerApi(this);
        lvGoals = (ListView) findViewById(R.id.lvGoals);
        goalAdapter = new GoalAdapter(getApplicationContext(), goalIds);

        lvGoals.setAdapter(goalAdapter);
        lvGoals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer goalId = goalAdapter.getItem(position);
                if (goalId == null) {
                    return;
                }
                startActivity(GoalTasksActivity.createIntentWithParams(
                        getApplicationContext(), goalId
                ));
            }
        });
        updateGoals();
    }

    public void updateGoals() {
        GetGoalRequirementsRequest req = new GetGoalRequirementsRequest();
        api.getScheduleModule().getGoalRequirements(req, new Response.Listener<GetGoalRequirementsResponse>() {
            @Override
            public void onResponse(GetGoalRequirementsResponse response) {
                goalAdapter.clear();

                if (response.goal_ids != null) {
                    goalAdapter.addAll(new ArrayList<>(Arrays.asList(response.goal_ids)));

                } else {
                    Toast.makeText(getApplicationContext(), "You have no goals, create one!", Toast.LENGTH_LONG).show();
                }

                Log.v("Goals", "updated, " + goalAdapter.getCount() + " total");
            }
        }, api.dialogErrorListener(this));
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateGoals();
    }

    public class GoalAdapter extends ArrayAdapter<Integer> {
        public GoalAdapter(@NonNull Context context, ArrayList<Integer> histories) {
            super(context, 0, histories);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Integer goalId = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_goal_summary, parent, false);
            }

            final TextView tvGoalTitle = (TextView) convertView.findViewById(R.id.tvGoalTitle);
            tvGoalTitle.setText("");

            GetSummaryOfGoalRequest req = new GetSummaryOfGoalRequest();
            req.goal_id = goalId;
            api.getScheduleModule().getSummaryOfGoal(req, new Response.Listener<GetSummaryOfGoalResponse>() {
                @Override
                public void onResponse(GetSummaryOfGoalResponse response) {
                    tvGoalTitle.setText(response.title);
                }
            }, api.dialogErrorListener(getApplicationContext()));

            return convertView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_top_goals, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_goal) {
            startActivity(CreateGoalActivity.createIntentWithParams(getApplicationContext(), null));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
