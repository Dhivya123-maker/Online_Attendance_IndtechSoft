package com.example.online_attendance_indtechsoft.Notification;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.online_attendance_indtechsoft.R;
import com.example.online_attendance_indtechsoft.Task.UpdateTaskActivity;
import com.example.online_attendance_indtechsoft.Task.ViewTaskActivity;
import com.example.online_attendance_indtechsoft.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity {

    List<NotificationModel> notificationModelList;
    NotificationAdapter notificationAdapter;
    RecyclerView recyclerView;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerView = findViewById(R.id.notification_recycle);
        back = findViewById(R.id.back);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        String URL = " https://irms.in/api/notification";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("responseeeeeeeee", response);
                notificationModelList = new ArrayList<>();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray data = jsonObject.getJSONArray("data");

                    for(int i=0;i<data.length();i++){
                        JSONObject json = data.getJSONObject(i);
                        String title  = json.getString("title");
                        String message = json.getString("message");
                        NotificationModel notificationModel = new NotificationModel();
                        notificationModel.setTitle(title);
                        notificationModel.setMsg(message);

                        notificationModelList.add(notificationModel);
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(NotificationActivity.this));
                    notificationAdapter = new NotificationAdapter(NotificationActivity.this, notificationModelList);
                    recyclerView.setAdapter(notificationAdapter);

                    String Success = jsonObject.getString("status");
                    String msg = jsonObject.getString("msg");

                    if (Success.equals("200")) {
                        Toast.makeText(NotificationActivity.this, msg, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(NotificationActivity.this, msg, Toast.LENGTH_SHORT).show();

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
                headers.put("Authorization", "Bearer " + PreferenceUtils.getToken(NotificationActivity.this));

                return headers;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(NotificationActivity.this);
        requestQueue.add(stringRequest);
    }
}