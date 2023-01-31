package EmployeeInfo;

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
import com.example.online_attendance_indtechsoft.R;
import com.example.online_attendance_indtechsoft.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import EmployeeAttendance.EmployeeAttendanceActivity;
import EmployeeAttendance.EmployeeAttendanceAdapter;
import EmployeeAttendance.EmployeeAttendanceModel;

public class EmployeeInfoActivity extends AppCompatActivity {
    RecyclerView employee_info_recycle;
    ImageView back;
    List<EmployeeInfoModel> employeeInfoModelList;
    EmployeeInfoAdapter employeeInfoAdapter;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_info);

        back = findViewById(R.id.back);
        employee_info_recycle = findViewById(R.id.employee_info_recycle);
        progressBar = findViewById(R.id.progressbar);

        progressBar.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        employee_details();



    }
    public  void employee_details(){
        String URL = "https://www.irms.in/api/employee_detail";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("attendance", response);

                progressBar.setVisibility(View.GONE);
                try {
                    employeeInfoModelList = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject records = jsonObject.getJSONObject("records");


                    String emp_id = records.getString("emp_id");
                    String emp_code = records.getString("emp_code");
                    String emp_name = records.getString("emp_name");
                    String email = records.getString("email");
                    String mobile = records.getString("mobile");

                    EmployeeInfoModel employeeInfoModel = new EmployeeInfoModel();
                    employeeInfoModel.setS_no(emp_id);
                    employeeInfoModel.setEmp_code(emp_code);
                    employeeInfoModel.setEmp_name(emp_name);
                    employeeInfoModel.setEmail(email);
                    employeeInfoModel.setMobile_no(mobile);

                    employeeInfoModelList.add(employeeInfoModel);

                    employeeInfoAdapter = new EmployeeInfoAdapter(EmployeeInfoActivity.this, employeeInfoModelList);
                    employee_info_recycle.setAdapter(employeeInfoAdapter);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(EmployeeInfoActivity.this) {
                        @Override
                        public boolean canScrollVertically() {
                            return false;
                        }
                    };
                    employee_info_recycle.setLayoutManager(linearLayoutManager);


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
                headers.put("Authorization", "Bearer " + PreferenceUtils.getToken(EmployeeInfoActivity.this));

                return headers;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(EmployeeInfoActivity.this);
        requestQueue.add(stringRequest);
    }
}