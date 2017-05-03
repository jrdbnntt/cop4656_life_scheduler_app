package com.jrdbnntt.android.cop4656.lifescheduler.app;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.jrdbnntt.android.cop4656.lifescheduler.R;
import com.jrdbnntt.android.cop4656.lifescheduler.api.LifeSchedulerApi;

import java.util.ArrayList;

public class TopGoalsActivity extends AppCompatActivity {

    private LifeSchedulerApi api;
    private GoalAdapter goalAdapter;

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
        ListView lvGoals = (ListView) findViewById(R.id.lvGoals);
        goalAdapter = new GoalAdapter(getApplicationContext(), new ArrayList<Integer>(), api, null);

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

        goalAdapter.refresh();
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        goalAdapter.refresh();
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
