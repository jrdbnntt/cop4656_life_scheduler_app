package com.jrdbnntt.android.cop4656.lifescheduler.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Response;
import com.jrdbnntt.android.cop4656.lifescheduler.R;
import com.jrdbnntt.android.cop4656.lifescheduler.api.LifeSchedulerApi;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.user.data.RegisterRequest;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.user.data.RegisterResponse;


public class RegisterActivity extends AppCompatActivity {

    EditText etUsername, etPassword, etFirstName, etLastName, etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
    }

    public void onSubmit(View view) {
        RegisterRequest req = new RegisterRequest();
        req.username = etUsername.getText().toString();
        req.password = etPassword.getText().toString();
        req.first_name = etFirstName.getText().toString();
        req.last_name = etLastName.getText().toString();
        req.email = etEmail.getText().toString();

        LifeSchedulerApi api = new LifeSchedulerApi(this);
        api.getUserModule().register(req, new Response.Listener<RegisterResponse>() {
            @Override
            public void onResponse(RegisterResponse response) {
                Intent intent;
                if (response.logged_in) {
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                } else {
                    intent = new Intent(getApplicationContext(), SplashLoginActivity.class);
                }

                startActivity(intent);

            }
        }, api.dialogErrorListener(this));

    }
}
