package com.jrdbnntt.android.cop4656.lifescheduler.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.jrdbnntt.android.cop4656.lifescheduler.R;
import com.jrdbnntt.android.cop4656.lifescheduler.api.LifeSchedulerApi;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.get.GetGoalRequirementsRequest;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.get.GetGoalRequirementsResponse;

import java.util.ArrayList;
import java.util.Arrays;

public class GoalTasksActivity extends AppCompatActivity {

    private static final String ARG_PARENT_GOAL_ID = "parentGoalId";

    private Integer parentGoalId;
    private LifeSchedulerApi api;
    private GoalAdapter goalAdapter;
    private TaskAdapter taskAdapter;

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

        parentGoalId = bundle.getInt(ARG_PARENT_GOAL_ID, -1);
        if (parentGoalId == -1) {
            Log.e(this.getClass().getName(), "Missing required parentGoalId param");
            finish();
        }
    }

    public void init() {
        api = new LifeSchedulerApi(this);
        ListView lvGoals = (ListView) findViewById(R.id.lvGoals);
        ListView lvTasks = (ListView) findViewById(R.id.lvTasks);
        goalAdapter = new GoalAdapter(this, new ArrayList<Integer>(), api, parentGoalId);
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

        taskAdapter = new TaskAdapter(this, new ArrayList<Integer>(), api);
        lvTasks.setAdapter(taskAdapter);
        lvTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer taskId = taskAdapter.getItem(position);
                if (taskId == null) {
                    return;
                }
                // TODO edit task
            }
        });

        updateGoalsAndTasks();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateGoalsAndTasks();
    }

    private void updateGoalsAndTasks() {
        GetGoalRequirementsRequest req = new GetGoalRequirementsRequest();
        req.parent_goal_id = parentGoalId;
        api.getScheduleModule().getGoalRequirements(req, new Response.Listener<GetGoalRequirementsResponse>() {
            @Override
            public void onResponse(GetGoalRequirementsResponse response) {
                goalAdapter.clear();
                taskAdapter.clear();

                if (response.goal_ids != null) {
                    goalAdapter.addAll(new ArrayList<>(Arrays.asList(response.goal_ids)));

                }
                if (response.task_ids != null) {
                    taskAdapter.addAll(new ArrayList<>(Arrays.asList(response.task_ids)));
                }

                taskAdapter.notifyDataSetChanged();
                goalAdapter.notifyDataSetChanged();

                Log.v("Goals", "updated, " + goalAdapter.getCount() + " total");
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
                    getApplicationContext(), parentGoalId
            ));
            return true;
        } else if (id == R.id.action_add_task) {
            startActivity(CreateTaskActivity.createIntentWithParams(
                    getApplicationContext(), parentGoalId
            ));
        }

        return super.onOptionsItemSelected(item);
    }





}
