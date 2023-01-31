package com.example.online_attendance_indtechsoft.LeadAllocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.online_attendance_indtechsoft.History.HistoryModel;
import com.example.online_attendance_indtechsoft.Leads.LeadActivity;
import com.example.online_attendance_indtechsoft.Leads.LeadInformationActivity;
import com.example.online_attendance_indtechsoft.Notification.NotificationActivity;
import com.example.online_attendance_indtechsoft.Notification.NotificationAdapter;
import com.example.online_attendance_indtechsoft.Notification.NotificationModel;
import com.example.online_attendance_indtechsoft.R;
import com.example.online_attendance_indtechsoft.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeadAllocationActivity extends AppCompatActivity {
    ImageView back;
    RecyclerView recyclerView;
    List<LeadAllocationModel> leadAllocationModelList;
    LeadAllocationAdapter leadAllocationAdapter;
    private static final int REQUEST_CALL = 1;
    String mobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_allocation);

        back = findViewById(R.id.back);
        recyclerView = findViewById(R.id.recyclerview);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        String URL = "https://irms.in/api/lead_list";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("leeaddd", response);

                try {
                    leadAllocationModelList = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONArray list = data.getJSONArray("list");

                    for(int i=0;i<list.length();i++)
                    {
                        JSONObject json = list.getJSONObject(i);
                         mobile = json.getString("mobile");
                         String Id = json.getString("lead_id");


                        LeadAllocationModel leadAllocationModel = new LeadAllocationModel();
                        leadAllocationModel.setMobile(mobile);
                        leadAllocationModel.setId(Id);
                        leadAllocationModel.setWind("Press here to wind up the call");

                        leadAllocationModelList.add(leadAllocationModel);
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(LeadAllocationActivity.this));
                    leadAllocationAdapter = new LeadAllocationAdapter(LeadAllocationActivity.this, leadAllocationModelList);
                    recyclerView.setAdapter(leadAllocationAdapter);


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
                headers.put("Authorization", "Bearer " + PreferenceUtils.getToken(LeadAllocationActivity.this));

                return headers;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(LeadAllocationActivity.this);
        requestQueue.add(stringRequest);


    }

   

}