package com.example.online_attendance_indtechsoft.Task;

import static android.view.View.GONE;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.online_attendance_indtechsoft.DashboardActivity;
import com.example.online_attendance_indtechsoft.LoginActivity;
import com.example.online_attendance_indtechsoft.R;
import com.example.online_attendance_indtechsoft.utils.PreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TaskAssignActivity extends AppCompatActivity {
    LinearLayout task_schedule;
    LinearLayout add_lnr;
    ImageView back;
    LinearLayout taskbar;
    Button submit;
    EditText task_name,assigned_by,assigned_to;
    String Task_name,Assigned_by,Assigned_to;
    ArrayList<String> task_name_array = new ArrayList<>();
    ArrayList<String> assigned_by_array = new ArrayList<>();
    ArrayList<String> assigned_to_array = new ArrayList<>();
    LinearLayout task_lnr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_assign);

        taskbar = findViewById(R.id.taskbar);
        submit = findViewById(R.id.submit);

        add_lnr = findViewById(R.id.add_lnr);
        back = findViewById(R.id.back);
        task_schedule = findViewById(R.id.task_schedule_lnr);
        task_lnr = (LinearLayout) findViewById(R.id.linear_lay);

        submit.setVisibility(GONE);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                add_task();
                checkIfValidAndRead();
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        add_lnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addView();
                submit.setVisibility(View.VISIBLE);
            }
        });
        }


    public boolean checkIfValidAndRead() {

        task_name_array.clear();
        assigned_by_array.clear();
        assigned_to_array.clear();


        boolean result = true;

        for(int i=0;i<task_lnr.getChildCount();i++){

            View entry = task_lnr.getChildAt(i);

            task_name= entry.findViewById(R.id.task_name);
            assigned_by= entry.findViewById(R.id.task_assigned_by_person);
            assigned_to= entry.findViewById(R.id.task_assigned_to_person);

            if(!task_name.getText().toString().equals("")){

                task_name_array.add(task_name.getText().toString());


            }else {
                result = false;
                Toast.makeText(this, "task name field is required", Toast.LENGTH_SHORT).show();

                break;
            }
            if(!assigned_by.getText().toString().equals("")){
                assigned_by_array.add(assigned_by.getText().toString());


            }else {
                result = false;
                Toast.makeText(this, "assigned by field is required", Toast.LENGTH_SHORT).show();

                break;
            }

            if(!assigned_to.getText().toString().equals("")){
                assigned_to_array.add(assigned_to.getText().toString());


            }else {
                result = false;
                Toast.makeText(this, "assigned to field is required", Toast.LENGTH_SHORT).show();

                break;
            }



        }


        return result;


    }

    public void addView() {

        View add = getLayoutInflater().inflate(R.layout.task_assign_recycle,null,false);

        LinearLayout remove_lnr;

        remove_lnr = add.findViewById(R.id.remove_lnr);
        task_name = add.findViewById(R.id.task_name);
        assigned_by = add.findViewById(R.id.task_assigned_by_person);
        assigned_to = add.findViewById(R.id.task_assigned_to_person);


        remove_lnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog;

                dialog = new Dialog(TaskAssignActivity.this);

                // set custom dialog
                dialog.setContentView(R.layout.alert_dailogbox);

                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                TextView title = dialog.findViewById(R.id.title);
                RelativeLayout no = dialog.findViewById(R.id.no);
                RelativeLayout yes = dialog.findViewById(R.id.yes);

                title.setText("Are you sure want to remove this task");


                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        removeView(add);
                        remove_task();

                    }
                });

            }
        });

        task_lnr.addView(add);

    }

    public void removeView(View view){

        task_lnr.removeView(view);

    }


    public void add_task() {
        if (checkIfValidAndRead()) {
            Task_name = task_name.getText().toString();
            Assigned_by = assigned_by.getText().toString();
            Assigned_to = assigned_to.getText().toString();

            String URL = "https://www.irms.in/api/add_task";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("responseeeeeeeee", response);

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        String Success = jsonObject.getString("status");
                        String msg = jsonObject.getString("msg");

                        if (Success.equals("200")) {
                            Toast.makeText(TaskAssignActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TaskAssignActivity.this, DashboardActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);


                        } else {
                            Toast.makeText(TaskAssignActivity.this, msg, Toast.LENGTH_SHORT).show();

                        }

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
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    for (int i = 0; i < task_lnr.getChildCount(); i++) {

                        params.put("task_name[" + i + "]", task_name_array.get(i));
                        params.put("assign_by[" + i + "]", assigned_by_array.get(i));
                        params.put("assign_to[" + i + "]", assigned_to_array.get(i));
                    }
                    Log.i("arrayyyyy", params.toString());
                    return params;

                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", "Bearer " + PreferenceUtils.getToken(TaskAssignActivity.this));

                    Log.i("tokeennnnnnnnn",PreferenceUtils.getToken(TaskAssignActivity.this));
                    return headers;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            RequestQueue requestQueue = Volley.newRequestQueue(TaskAssignActivity.this);
            requestQueue.add(stringRequest);

        }
    }

    public  void remove_task(){
        String URL = "https://www.irms.in/api/task_delete";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("responseeeeeeeee", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String Success = jsonObject.getString("status");
                    String msg = jsonObject.getString("msg");

                    if (Success.equals("200")) {
                        if(task_lnr.getChildCount()<1){
                            submit.setVisibility(GONE);
                        }else{
                            submit.setVisibility(View.VISIBLE);
                        }
                        Toast.makeText(TaskAssignActivity.this, msg, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(TaskAssignActivity.this, msg, Toast.LENGTH_SHORT).show();

                    }

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
                headers.put("Authorization", "Bearer " + PreferenceUtils.getToken(TaskAssignActivity.this));

                return headers;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(TaskAssignActivity.this);
        requestQueue.add(stringRequest);
    }

}