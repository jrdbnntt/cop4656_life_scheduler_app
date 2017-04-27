package com.jrdbnntt.android.cop4656.lifescheduler.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.get.summary.GetSummaryOfTaskRequest;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.get.summary.GetSummaryOfTaskResponse;

import java.util.ArrayList;
import java.util.Arrays;

public class GoalTasksActivity extends AppCompatActivity {

    private static final String ARG_PARENT_GOAL_ID = "parent_goal_id";

    private Integer parent_goal_id;
    LifeSchedulerApi api;


    public static Intent createIntentWithParams(Context context, Integer parent_goal_id) {
        Intent intent = new Intent(context, GoalTasksActivity.class);
        intent.putExtra(ARG_PARENT_GOAL_ID, parent_goal_id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_tasks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getParams();
        init();
    }

    private void getParams() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        parent_goal_id = bundle.getInt(ARG_PARENT_GOAL_ID, -1);
        if (parent_goal_id == -1) {
            Log.e(this.getClass().getName(), "Missing required parent_goal_id param");
            finish();
        }
    }

    public void init() {
        api = new LifeSchedulerApi(this);
        final ListView lvGoals = (ListView) findViewById(R.id.lvGoals);
        final ListView lvTasks = (ListView) findViewById(R.id.lvTasks);
        GetGoalRequirementsRequest req = new GetGoalRequirementsRequest();
        req.parent_goal_id = parent_goal_id;
        api.getScheduleModule().getGoalRequirements(req, new Response.Listener<GetGoalRequirementsResponse>() {
            @Override
            public void onResponse(GetGoalRequirementsResponse response) {
                Log.d("ids", response.toString());
                ArrayList<Integer> goalIds = new ArrayList<>(Arrays.asList(response.goal_ids));;
                ArrayList<Integer> taskIds = new ArrayList<>(Arrays.asList(response.task_ids));;

                final GoalAdapter goalAdapter = new GoalAdapter(getApplicationContext(), goalIds);
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

                final TaskAdapter taskAdapter = new TaskAdapter(getApplicationContext(), taskIds);
                lvTasks.setAdapter(taskAdapter);
                lvTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Integer taskId = taskAdapter.getItem(position);
                        if (taskId == null) {
                            return;
                        }
                        // TODO
                    }
                });
            }
        }, api.dialogErrorListener(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_goal_tasks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_goal) {
            startActivity(CreateGoalActivity.createIntentWithParams(
                    getApplicationContext(), parent_goal_id
            ));
            return true;
        } else if (id == R.id.action_add_task) {
            startActivity(CreateTaskActivity.createIntentWithParams(
                    getApplicationContext(), parent_goal_id
            ));
        }

        return super.onOptionsItemSelected(item);
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

    public class TaskAdapter extends ArrayAdapter<Integer> {
        public TaskAdapter(@NonNull Context context, ArrayList<Integer> histories) {
            super(context, 0, histories);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Integer taskId = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_goal_summary, parent, false);
            }

            final TextView tvTitle = (TextView) convertView.findViewById(R.id.tvGoalTitle);
            tvTitle.setText("");

            GetSummaryOfTaskRequest req = new GetSummaryOfTaskRequest();
            req.task_id = taskId;
            api.getScheduleModule().getSummaryOfTask(req, new Response.Listener<GetSummaryOfTaskResponse>() {
                @Override
                public void onResponse(GetSummaryOfTaskResponse response) {
                    tvTitle.setText(response.title);
                }
            }, api.dialogErrorListener(getApplicationContext()));

            return convertView;
        }
    }

}
