package EmployeeAttendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.online_attendance_indtechsoft.History.HistoryActivity;
import com.example.online_attendance_indtechsoft.History.HistoryAdapter;
import com.example.online_attendance_indtechsoft.History.HistoryModel;
import com.example.online_attendance_indtechsoft.R;
import com.example.online_attendance_indtechsoft.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import EmployeeInfo.EmployeeInfoActivity;
import EmployeeInfo.EmployeeInfoAdapter;
import EmployeeInfo.EmployeeInfoModel;

public class EmployeeAttendanceActivity extends AppCompatActivity {
    RecyclerView employee_attendance_recycle;
    ImageView back;
    List<EmployeeAttendanceModel> employeeAttendanceModelList;
    EmployeeAttendanceAdapter employeeAttendanceAdapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_attendance);

        back = findViewById(R.id.back);
        progressBar = findViewById(R.id.progressbar);
        employee_attendance_recycle = findViewById(R.id.employee_attendance_recycle);

        progressBar.setVisibility(View.VISIBLE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        employee_attendance();


    }

    public  void employee_attendance(){

        String URL = "https://www.irms.in/api/employee_attendance";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("attendance", response);

                progressBar.setVisibility(View.GONE);

                try {
                    employeeAttendanceModelList = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray records = jsonObject.getJSONArray("records");

                    for (int i = 0; i <records.length();i++) {
                        JSONObject records_jsonobject = records.getJSONObject(i);
                        String s_no = records_jsonobject.getString("s_no");
                        String emp_name = records_jsonobject.getString("emp_name");
                        String user_role = records_jsonobject.getString("user_role");
                        String login_time = records_jsonobject.getString("login_time");
                        String logout_time = records_jsonobject.getString("logout_time");
                        String lunch_start_time = records_jsonobject.getString("lunch_start_time");
                        String lunch_end_time = records_jsonobject.getString("lunch_end_time");
                        String tea_start_time = records_jsonobject.getString("tea_start_time");
                        String tea_end_time = records_jsonobject.getString("tea_end_time");
                        String travel_start_time = records_jsonobject.getString("travel_start_time");
                        String travel_end_time = records_jsonobject.getString("travel_end_time");
                        String meeting_start_time = records_jsonobject.getString("meeting_start_time");
                        String meeting_end_time = records_jsonobject.getString("meeting_end_time");
                        String break_start_time = records_jsonobject.getString("break_start_time");
                        String break_end_time = records_jsonobject.getString("break_end_time");
                        String permission_start_time = records_jsonobject.getString("permission_start_time");
                        String permission_end_time = records_jsonobject.getString("permission_end_time");

                        EmployeeAttendanceModel employeeAttendanceModel = new EmployeeAttendanceModel();
                        if(s_no.equals("")){
                            employeeAttendanceModel.setS_no("-");
                        }else{
                            employeeAttendanceModel.setS_no(s_no);
                        }
                        if(emp_name.equals("")){
                            employeeAttendanceModel.setName("-");
                        }else{
                            employeeAttendanceModel.setName(emp_name);
                        }
                        if(user_role.equals("")){
                            employeeAttendanceModel.setUser_role("-");
                        }else{
                            employeeAttendanceModel.setUser_role(user_role);
                        }
                        if(login_time.equals("")){
                            employeeAttendanceModel.setLogin_time("-");
                        }else{
                            employeeAttendanceModel.setLogin_time(login_time);
                        }
                        if(logout_time.equals("")){
                            employeeAttendanceModel.setLogout_time("-");
                        }else{
                            employeeAttendanceModel.setLogout_time(logout_time);
                        }
                        if(lunch_start_time.equals("")){
                            employeeAttendanceModel.setLunch_start("-");
                        }else{
                            employeeAttendanceModel.setLunch_start(lunch_start_time);
                        }

                        if(lunch_end_time.equals("")){
                            employeeAttendanceModel.setLunch_end("-");
                        }else{
                            employeeAttendanceModel.setLunch_end(lunch_end_time);
                        }
                        if(tea_start_time.equals("")){
                            employeeAttendanceModel.setTea_start(tea_start_time);
                        }else{
                            employeeAttendanceModel.setTea_start(tea_start_time);
                        }

                        if(tea_end_time.equals("")){
                            employeeAttendanceModel.setTea_end("-");
                        }else{
                            employeeAttendanceModel.setTea_end(tea_end_time);
                        }
                        if(travel_start_time.equals("")){
                            employeeAttendanceModel.setTravel_start("-");
                        }else{
                            employeeAttendanceModel.setTravel_start(travel_start_time);
                        }

                        if(travel_end_time.equals("")){
                            employeeAttendanceModel.setTravel_end("-");
                        }else{
                            employeeAttendanceModel.setTravel_end(travel_end_time);
                        }

                        if(meeting_start_time.equals("")){
                            employeeAttendanceModel.setMeeting_start("-");
                        }else{
                            employeeAttendanceModel.setMeeting_start(meeting_start_time);
                        }

                        if(meeting_end_time.equals("")){
                            employeeAttendanceModel.setMeeting_end("-");
                        }else{
                            employeeAttendanceModel.setMeeting_end(meeting_end_time);
                        }

                        if(break_start_time.equals("")){
                            employeeAttendanceModel.setBreak_start("-");
                        }else{
                            employeeAttendanceModel.setBreak_start(break_start_time);
                        }

                        if(break_end_time.equals("")){
                            employeeAttendanceModel.setBreak_end("-");
                        }else{
                            employeeAttendanceModel.setBreak_end(break_end_time);
                        }
                        if(permission_start_time.equals("")){
                            employeeAttendanceModel.setPermission_start("-");
                        }else{
                            employeeAttendanceModel.setPermission_start(permission_start_time);
                        }
                        if(permission_end_time.equals("")){
                            employeeAttendanceModel.setPermission_end("-");

                        }else{
                            employeeAttendanceModel.setPermission_end(permission_end_time);

                        }


                        employeeAttendanceModelList.add(employeeAttendanceModel);
                    }
                    employeeAttendanceAdapter = new EmployeeAttendanceAdapter(EmployeeAttendanceActivity.this, employeeAttendanceModelList);
                    employee_attendance_recycle.setAdapter(employeeAttendanceAdapter);
                    employee_attendance_recycle.setLayoutManager(new LinearLayoutManager(EmployeeAttendanceActivity.this));
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(EmployeeAttendanceActivity.this) {
                        @Override
                        public boolean canScrollVertically() {
                            return false;
                        }
                    };
                    employee_attendance_recycle.setLayoutManager(linearLayoutManager);


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
                headers.put("Authorization", "Bearer " + PreferenceUtils.getToken(EmployeeAttendanceActivity.this));

                return headers;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(EmployeeAttendanceActivity.this);
        requestQueue.add(stringRequest);
    }

}