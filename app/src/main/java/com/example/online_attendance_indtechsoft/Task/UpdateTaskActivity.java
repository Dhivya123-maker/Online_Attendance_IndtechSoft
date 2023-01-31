package com.example.online_attendance_indtechsoft.Task;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.example.online_attendance_indtechsoft.R;
import com.example.online_attendance_indtechsoft.utils.PreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class UpdateTaskActivity extends AppCompatActivity {
    LinearLayout update;
    String id,name,assign_by,assign_to;
    EditText task_id,task_name,task_assign_by,task_assign_to;
    TextView error1,error2,error3,error4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        assign_by = intent.getStringExtra("assign_by");
        assign_to = intent.getStringExtra("assign_to");

        update = findViewById(R.id.update_lnr);
        task_id = findViewById(R.id.task_id);
        task_name = findViewById(R.id.task_name);
        task_assign_by = findViewById(R.id.task_assigned_by_person);
        task_assign_to = findViewById(R.id.task_assigned_to_person);

        error1 = findViewById(R.id.error1);
        error2 = findViewById(R.id.error2);
        error3 = findViewById(R.id.error3);
        error4 = findViewById(R.id.error4);




        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(task_id.getText().toString().isEmpty()){
                    error1.setVisibility(View.VISIBLE);
                    error2.setVisibility(View.GONE);
                    error3.setVisibility(View.GONE);
                    error4.setVisibility(View.GONE);
                }else if(task_name.getText().toString().isEmpty()){
                    error2.setVisibility(View.VISIBLE);
                    error1.setVisibility(View.GONE);
                    error3.setVisibility(View.GONE);
                    error4.setVisibility(View.GONE);
                }else if(task_assign_by.getText().toString().isEmpty()){
                    error3.setVisibility(View.VISIBLE);
                    error2.setVisibility(View.GONE);
                    error1.setVisibility(View.GONE);
                    error4.setVisibility(View.GONE);

                }else if(task_assign_to.getText().toString().isEmpty()){
                    error4.setVisibility(View.VISIBLE);
                    error3.setVisibility(View.GONE);
                    error2.setVisibility(View.GONE);
                    error1.setVisibility(View.GONE);
                }else if(task_id.getText().toString().equals(id)){
                    update_task();
                }else{
                    error4.setVisibility(View.VISIBLE);
                    error4.setText("task id is incorrect");
                }

            }
        });

    }
    public  void update_task(){
        String URL = "https://www.irms.in/api/task_update";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("responseeeeeeeee", response);


                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String Success = jsonObject.getString("status");
                    String msg = jsonObject.getString("msg");

                    if (Success.equals("200")) {

                        Toast.makeText(UpdateTaskActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UpdateTaskActivity.this, ViewTaskActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);


                    } else {
                        Toast.makeText(UpdateTaskActivity.this, msg, Toast.LENGTH_SHORT).show();

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
                params.put("task_id",task_id.getText().toString());
                params.put("task_name",task_name.getText().toString());
                params.put("assign_by",task_assign_by.getText().toString());
                params.put("assign_to",task_assign_to.getText().toString());


                Log.i("arrayyyyy", params.toString());
                return params;

            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer " + PreferenceUtils.getToken(UpdateTaskActivity.this));

                return headers;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(UpdateTaskActivity.this);
        requestQueue.add(stringRequest);
    }
}