package com.jrdbnntt.android.cop4656.lifescheduler.app;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.jrdbnntt.android.cop4656.lifescheduler.R;
import com.jrdbnntt.android.cop4656.lifescheduler.api.LifeSchedulerApi;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.create.CreateGoalRequest;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.create.CreateGoalResponse;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.create.CreateTaskRequest;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.create.CreateTaskResponse;

public class CreateTaskActivity extends AppCompatActivity {

    private static final String ARG_PARENT_GOAL_ID = "parent_goal_id";

    private EditText etTitle;
    private EditText etDescription;
    private EditText etLocation;
    private EditText etPriority;
    private EditText etEstimatedTime;
    private Button bSubmit;
    private LifeSchedulerApi api;
    private Integer parent_goal_id;


    public static Intent createIntentWithParams(Context context, Integer parent_goal_id) {
        Intent intent = new Intent(context, CreateTaskActivity.class);
        intent.putExtra(ARG_PARENT_GOAL_ID, parent_goal_id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
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

    private void init() {
        api = new LifeSchedulerApi(this);

        etTitle = (EditText) findViewById(R.id.etTitle);
        etDescription = (EditText) findViewById(R.id.etDescription);
        etLocation = (EditText) findViewById(R.id.etLocation);
        etPriority = (EditText) findViewById(R.id.etPriority);
        etEstimatedTime = (EditText) findViewById(R.id.etEstimatedTime);
        bSubmit = (Button) findViewById(R.id.bSubmit);

        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }


    private void submit() {
        String text;
        CreateTaskRequest req = new CreateTaskRequest();
        req.title = etTitle.getText().toString();
        req.location = etLocation.getText().toString();
        req.description = etDescription.getText().toString();
        req.task_type = 2; // TODO

        try {
            text = etPriority.getText().toString();
            if (text.length() > 0) {
                req.priority = Integer.parseInt(text);
            }
            text = etEstimatedTime.getText().toString();
            if (text.length() > 0) {
                req.total_time_required_m = Integer.parseInt(text);
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (parent_goal_id != null) {
            req.parent_goal_id = parent_goal_id;
        }

        api.getScheduleModule().createTask(req, new Response.Listener<CreateTaskResponse>() {
            @Override
            public void onResponse(CreateTaskResponse response) {
                finish();
            }
        }, api.dialogErrorListener(this));

    }
}
