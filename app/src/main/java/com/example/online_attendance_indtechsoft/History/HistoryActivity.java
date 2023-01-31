package com.example.online_attendance_indtechsoft.History;

import android.app.DatePickerDialog;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.online_attendance_indtechsoft.DashboardActivity;
import com.example.online_attendance_indtechsoft.R;
import com.example.online_attendance_indtechsoft.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<HistoryModel> historyModelList;
    HistoryAdapter historyAdapter;
    ImageView back,calender_img;
    Calendar calendar;
    TextView date_txt,no_data;
    String url;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.location_history_recycle);
        back = findViewById(R.id.back);
        calender_img = findViewById(R.id.calender_img);
        date_txt = findViewById(R.id.date_txt);
        no_data = findViewById(R.id.no_data);
        progressBar = findViewById(R.id.progressbar);


        progressBar.setVisibility(View.VISIBLE);
         url = "https://www.irms.in/api/location_history";
         location_history(url);

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

                        date_txt.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                        location_history(url);


                    }
                };

                new DatePickerDialog(HistoryActivity.this, dateA, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



    }
    public  void location_history(String url) {
        if (date_txt.getText().equals("Choose date")) {
            progressBar.setVisibility(View.GONE);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("url", url);
                    Log.i("response", response);
                    historyModelList = new ArrayList<>();

                    try {
                        JSONObject jsonObject = new JSONObject(response);


                        JSONArray records = jsonObject.getJSONArray("records");


                        for (int i = 0; i <records.length();i++) {
                            JSONObject records_jsonobject = records.getJSONObject(i);

                            String location = records_jsonobject.getString("location");
                            String time = records_jsonobject.getString("check_in_time");

                            HistoryModel historyModel = new HistoryModel();
                            historyModel.setLocation(location);
                            historyModel.setTime(time);

                            historyModelList.add(historyModel);
                        }

                        historyAdapter = new HistoryAdapter(HistoryActivity.this, historyModelList);
                        recyclerView.setAdapter(historyAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));

                        if (records.toString().equals("[]")){
                            no_data.setVisibility(View.VISIBLE);
                        }else {
                            no_data.setVisibility(View.GONE);
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
                    headers.put("Authorization", "Bearer " +PreferenceUtils.getToken(HistoryActivity.this));

                    return headers;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            RequestQueue requestQueue = Volley.newRequestQueue(HistoryActivity.this);
            requestQueue.add(stringRequest);
        }
        else{

            progressBar.setVisibility(View.GONE);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("responseeeeeeeee", response);
                    historyModelList = new ArrayList<>();

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        JSONArray records = jsonObject.getJSONArray("records");
                        if (records.toString().equals("[]")){
                            no_data.setVisibility(View.VISIBLE);
                        }else {
                            no_data.setVisibility(View.GONE);
                        }
                        for (int i = 0; i <records.length();i++) {
                            JSONObject records_jsonobject = records.getJSONObject(i);

                            String location = records_jsonobject.getString("location");
                            String time = records_jsonobject.getString("check_in_time");

                            HistoryModel historyModel = new HistoryModel();
                            historyModel.setLocation(location);
                            historyModel.setTime(time);

                            historyModelList.add(historyModel);
                        }

                        historyAdapter = new HistoryAdapter(HistoryActivity.this, historyModelList);
                        recyclerView.setAdapter(historyAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));


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

                    params.put("search",date_txt.getText().toString());

                    Log.i("arrayyyyy", params.toString());
                    return params;

                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", "Bearer " + PreferenceUtils.getToken(HistoryActivity.this));

                    return headers;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            RequestQueue requestQueue = Volley.newRequestQueue(HistoryActivity.this);
            requestQueue.add(stringRequest);
        }
    }
}