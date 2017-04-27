package com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule;

import com.android.volley.Response;
import com.jrdbnntt.android.cop4656.lifescheduler.api.LifeSchedulerApi;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.GenerateRequest;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.GenerateResponse;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.create.CreateGoalRequest;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.create.CreateGoalResponse;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.create.CreateTaskRequest;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.create.CreateTaskResponse;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.delete.DeleteGoalRequest;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.delete.DeleteTaskRequest;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.get.GetGoalRequirementsRequest;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.get.GetGoalRequirementsResponse;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.get.GetTimeAllotmentsRequest;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.get.GetTimeAllotmentsResponse;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.get.details.GetDetailsOfGoalRequest;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.get.details.GetDetailsOfGoalResponse;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.get.details.GetDetailsOfTaskRequest;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.get.details.GetDetailsOfTaskResponse;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.get.summary.GetSummaryOfGoalRequest;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.get.summary.GetSummaryOfGoalResponse;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.get.summary.GetSummaryOfTaskRequest;
import com.jrdbnntt.android.cop4656.lifescheduler.api.modules.schedule.data.get.summary.GetSummaryOfTaskResponse;
import com.jrdbnntt.android.std.api.ApiModule;
import com.jrdbnntt.android.std.api.data.EmptyResponse;

/**
 * TODO
 */
public class ScheduleModule extends ApiModule<LifeSchedulerApi> {
    public ScheduleModule(LifeSchedulerApi api) {
        super(api);
    }


    public void generate(
            GenerateRequest req,
            Response.Listener<GenerateResponse> res,
            Response.ErrorListener err
    ) {
        api.sendPost("/schedule/generate", req, GenerateResponse.class, null, res, err);
    }

    public void createGoal(
            CreateGoalRequest req,
            Response.Listener<CreateGoalResponse> res,
            Response.ErrorListener err
    ) {
        api.sendPost("/schedule/create/goal", req, CreateGoalResponse.class, null, res, err);
    }

    public void createTask(
            CreateTaskRequest req,
            Response.Listener<CreateTaskResponse> res,
            Response.ErrorListener err
    ) {
        api.sendPost("/schedule/create/task", req, CreateTaskResponse.class, null, res, err);
    }

    public void deleteGoal(
            DeleteGoalRequest req,
            Response.Listener<EmptyResponse> res,
            Response.ErrorListener err
    ) {
        api.sendPost("/schedule/delete/goal", req, EmptyResponse.class, null, res, err);
    }

    public void deleteTask(
            DeleteTaskRequest req,
            Response.Listener<EmptyResponse> res,
            Response.ErrorListener err
    ) {
        api.sendPost("/schedule/delete/task", req, EmptyResponse.class, null, res, err);
    }

    public void getGoalRequirements(
            GetGoalRequirementsRequest req,
            Response.Listener<GetGoalRequirementsResponse> res,
            Response.ErrorListener err
    ) {
        api.sendPost("/schedule/get/goal_requirements$", req,
                GetGoalRequirementsResponse.class, null, res, err);
    }


    public void getTimeAllotments(
            GetTimeAllotmentsRequest req,
            Response.Listener<GetTimeAllotmentsResponse> res,
            Response.ErrorListener err
    ) {
        api.sendPost("/schedule/get/time_allotments$", req,
                GetTimeAllotmentsResponse.class, null, res, err);
    }

    public void getDetailsOfGoal(
            GetDetailsOfGoalRequest req,
            Response.Listener<GetDetailsOfGoalResponse> res,
            Response.ErrorListener err
    ) {
        api.sendPost("/schedule/get/details/goal", req,
                GetDetailsOfGoalResponse.class, null, res, err);
    }

    public void getDetailsOfTask(
            GetDetailsOfTaskRequest req,
            Response.Listener<GetDetailsOfTaskResponse> res,
            Response.ErrorListener err
    ) {
        api.sendPost("/schedule/get/details/task", req,
                GetDetailsOfTaskResponse.class, null, res, err);
    }

    public void getSummaryOfTask(
            GetSummaryOfTaskRequest req,
            Response.Listener<GetSummaryOfTaskResponse> res,
            Response.ErrorListener err
    ) {
        api.sendPost("/schedule/get/summary/task", req,
                GetSummaryOfTaskResponse.class, null, res, err);
    }

    public void getSummaryOfGoal(
            GetSummaryOfGoalRequest req,
            Response.Listener<GetSummaryOfGoalResponse> res,
            Response.ErrorListener err
    ) {
        api.sendPost("/schedule/get/summary/goal", req,
                GetSummaryOfGoalResponse.class, null, res, err);
    }



}
