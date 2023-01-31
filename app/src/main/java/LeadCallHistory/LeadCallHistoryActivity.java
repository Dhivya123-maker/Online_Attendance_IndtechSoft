package LeadCallHistory;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LeadCallHistoryActivity extends AppCompatActivity {

    RecyclerView call_log_recycle;
    List<LeadCallHistoryModel> leadCallHistoryModelList;
    LeadCallHistoryAdpter leadCallHistoryAdpter;
    ImageView back;


    @SuppressLint("Range")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_history);

        call_log_recycle = findViewById(R.id.call_log_recycle);

        back = findViewById(R.id.back);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        String URL = "https://irms.in/api/lead_logs";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("leeaddd", response);

                try {
                    leadCallHistoryModelList = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for(int i=0;i<data.length();i++){
                        JSONObject json = data.getJSONObject(i);
                        String first_name = json.getString("first_name");
                        String last_name = json.getString("last_name");
                        String mobile = json.getString("mobile");
                        String start_time = json.getString("start_time");
                        String end_time = json.getString("end_time");
                        String duration = json.getString("duration");

                        LeadCallHistoryModel leadCallHistoryModel = new LeadCallHistoryModel();
                        leadCallHistoryModel.setName(first_name+last_name);
                        leadCallHistoryModel.setNumber(mobile);
                        leadCallHistoryModel.setStart_time(start_time);
                        leadCallHistoryModel.setEnd_time(end_time);

                        if(duration.equals("")){
                            leadCallHistoryModel.setDuration("0");
                        }else{
                            leadCallHistoryModel.setDuration(duration);
                        }

                        leadCallHistoryModelList.add(leadCallHistoryModel);

                    }
                    call_log_recycle.setLayoutManager(new LinearLayoutManager(LeadCallHistoryActivity.this));
                    leadCallHistoryAdpter = new LeadCallHistoryAdpter(LeadCallHistoryActivity.this, leadCallHistoryModelList);
                    call_log_recycle.setAdapter(leadCallHistoryAdpter);


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
                headers.put("Authorization", "Bearer " + PreferenceUtils.getToken(LeadCallHistoryActivity.this));

                return headers;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(LeadCallHistoryActivity.this);
        requestQueue.add(stringRequest);

    }

}