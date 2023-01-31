package com.example.online_attendance_indtechsoft.Task;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.online_attendance_indtechsoft.DashboardActivity;
import com.example.online_attendance_indtechsoft.R;
import com.example.online_attendance_indtechsoft.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewTaskActivity extends AppCompatActivity  {
    List<ViewTaskModel> taskModelList;
    ViewTaskAdapter taskAdapter;
    RecyclerView recyclerView;
    ImageView calender_img;
    Calendar calendar;
    TextView date_txt;
    ImageView back;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        recyclerView = findViewById(R.id.task_list);

        calender_img = findViewById(R.id.calender_img);
        date_txt = findViewById(R.id.date_txt);
        back = findViewById(R.id.back);
        progressBar = findViewById(R.id.progressbar);

        progressBar.setVisibility(View.VISIBLE);


        view_task();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        calender_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener dateA = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        date_txt.setText(dayOfMonth+"-"+(monthOfYear+1)+"-"+year);


                    }
                };

                new DatePickerDialog(ViewTaskActivity.this, dateA, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



    }
    public void view_task(){

        String URL = "https://www.irms.in/api/task_list";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("responseeeeeeeee", response);
                progressBar.setVisibility(View.GONE);
                try {
                    taskModelList = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("records");

                    for(int i=0;i<=jsonArray.length()-1;i++){
                        JSONObject task_json = jsonArray.getJSONObject(i);
                        String task_id = task_json.getString("task_id");
                        String task_name = task_json.getString("task_name");
                        String assign_by = task_json.getString("assign_by");
                        String assign_to = task_json.getString("assign_to");

                        ViewTaskModel taskModel = new ViewTaskModel();

                        taskModel.setTask_id(task_id);
                        taskModel.setTask_name(task_name);
                        taskModel.setTask_assigned_by(assign_by);
                        taskModel.setTask_assigned_to(assign_to);

                        taskModelList.add(taskModel);

                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(ViewTaskActivity.this));
                    taskAdapter = new ViewTaskAdapter(ViewTaskActivity.this, taskModelList);
                    recyclerView.setAdapter(taskAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer " + PreferenceUtils.getToken(ViewTaskActivity.this));

                return headers;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(ViewTaskActivity.this);
        requestQueue.add(stringRequest);
    }
    public void onBackPressed() {
    startActivity(new Intent(ViewTaskActivity.this, DashboardActivity.class));
    }


}