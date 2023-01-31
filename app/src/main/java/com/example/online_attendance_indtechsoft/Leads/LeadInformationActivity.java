package com.example.online_attendance_indtechsoft.Leads;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LeadInformationActivity extends AppCompatActivity {
    ImageView back;
    Spinner property_cat,property_reg;
    Button submit;
    RelativeLayout relativeLayout;
    EditText first,last,mobile,email,budget,other;
    TextView error1,error2,error3,error4,error5,error6,error7,error8;
    String region_name,category_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_information);

        back = findViewById(R.id.back);
        property_cat = findViewById(R.id.property_cat);
        property_reg = findViewById(R.id.property_reg);
        submit = findViewById(R.id.submit);
        first = findViewById(R.id.first_name);
        last = findViewById(R.id.last_name);
        mobile = findViewById(R.id.mobile_num);
        email = findViewById(R.id.email);
        budget = findViewById(R.id.budget);
        other = findViewById(R.id.other);
        error1 = findViewById(R.id.error1);
        error2 = findViewById(R.id.error2);
        error3 = findViewById(R.id.error3);
        error4 = findViewById(R.id.error4);
        error5 = findViewById(R.id.error5);
        error6 = findViewById(R.id.error6);
        error7 = findViewById(R.id.error7);
        error8 = findViewById(R.id.error8);

        String URL = "https://irms.in/api/list_cat_region";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("leeaddd", response);

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONArray region = data.getJSONArray("region");

                    for(int i=0;i<region.length();i++){
                        JSONObject json = region.getJSONObject(i);
                         region_name = json.getString("region_name");

                        ArrayList sname = new ArrayList();
                        sname.add("Select Location");
                        sname.add(region_name);

                        ArrayAdapter<String> Adapter2 = new ArrayAdapter<String>(LeadInformationActivity.this,
                                R.layout.spinner_text,sname);
                        Adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        property_reg.setAdapter(Adapter2);

                    }
                    JSONArray category = data.getJSONArray("category");

                    for(int i=0;i<category.length();i++){
                        JSONObject json = category.getJSONObject(i);
                        category_name = json.getString("category_name");

                        ArrayList sname = new ArrayList();
                        sname.add("Select Category");
                        sname.add(category_name);

                        ArrayAdapter<String> Adapter1 = new ArrayAdapter<String>(LeadInformationActivity.this,
                                R.layout.spinner_text, sname);
                        Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        property_cat.setAdapter(Adapter1);


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
                headers.put("Authorization", "Bearer " + PreferenceUtils.getToken(LeadInformationActivity.this));

                return headers;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(LeadInformationActivity.this);
        requestQueue.add(stringRequest);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String FirstName = first.getText().toString();
                String LastName = last.getText().toString();
                String MobileNo = mobile.getText().toString();
                String Email = email.getText().toString();
                String Budget = budget.getText().toString();
                String Property = property_cat.getSelectedItem().toString();
                String Region = property_reg.getSelectedItem().toString();
                String Other = other.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String MobilePattern = "[0-9]{10}";

                if(FirstName.isEmpty()){
                 error1.setVisibility(View.VISIBLE);
                 error2.setVisibility(View.GONE);
                 error3.setVisibility(View.GONE);
                 error4.setVisibility(View.GONE);
                 error5.setVisibility(View.GONE);
                 error6.setVisibility(View.GONE);
                 error7.setVisibility(View.GONE);
                 error8.setVisibility(View.GONE);
                }else if(LastName.isEmpty()){
                    error1.setVisibility(View.GONE);
                    error2.setVisibility(View.VISIBLE);
                    error3.setVisibility(View.GONE);
                    error4.setVisibility(View.GONE);
                    error5.setVisibility(View.GONE);
                    error6.setVisibility(View.GONE);
                    error7.setVisibility(View.GONE);
                    error8.setVisibility(View.GONE);
                }else if(MobileNo.isEmpty()){
                    error1.setVisibility(View.GONE);
                    error2.setVisibility(View.GONE);
                    error3.setVisibility(View.VISIBLE);
                    error4.setVisibility(View.GONE);
                    error5.setVisibility(View.GONE);
                    error6.setVisibility(View.GONE);
                    error7.setVisibility(View.GONE);
                    error8.setVisibility(View.GONE);
                }else if(Email.isEmpty()){
                    error1.setVisibility(View.GONE);
                    error2.setVisibility(View.GONE);
                    error3.setVisibility(View.GONE);
                    error4.setVisibility(View.VISIBLE);
                    error5.setVisibility(View.GONE);
                    error6.setVisibility(View.GONE);
                    error7.setVisibility(View.GONE);
                    error8.setVisibility(View.GONE);
                }
                else if(Property.equals("Select Category")){
                    error1.setVisibility(View.GONE);
                    error2.setVisibility(View.GONE);
                    error3.setVisibility(View.GONE);
                    error4.setVisibility(View.GONE);
                    error5.setVisibility(View.VISIBLE);
                    error6.setVisibility(View.GONE);
                    error7.setVisibility(View.GONE);
                    error8.setVisibility(View.GONE);

                }else if(Region.equals("Select Location")){
                    error1.setVisibility(View.GONE);
                    error2.setVisibility(View.GONE);
                    error3.setVisibility(View.GONE);
                    error4.setVisibility(View.GONE);
                    error5.setVisibility(View.GONE);
                    error6.setVisibility(View.VISIBLE);
                    error7.setVisibility(View.GONE);
                    error8.setVisibility(View.GONE);
                }
                else if(Budget.isEmpty()){
                    error1.setVisibility(View.GONE);
                    error2.setVisibility(View.GONE);
                    error3.setVisibility(View.GONE);
                    error4.setVisibility(View.GONE);
                    error5.setVisibility(View.GONE);
                    error6.setVisibility(View.GONE);
                    error7.setVisibility(View.VISIBLE);
                    error8.setVisibility(View.GONE);
                }else if(Other.isEmpty()){
                    error1.setVisibility(View.GONE);
                    error2.setVisibility(View.GONE);
                    error3.setVisibility(View.GONE);
                    error4.setVisibility(View.GONE);
                    error5.setVisibility(View.GONE);
                    error6.setVisibility(View.GONE);
                    error7.setVisibility(View.GONE);
                    error8.setVisibility(View.VISIBLE);
                }
                else if(!MobileNo.matches(MobilePattern))
                {
                    error1.setVisibility(View.GONE);
                    error2.setVisibility(View.GONE);
                    error3.setVisibility(View.VISIBLE);
                    error3.setText("enter a valid mobile number");
                    error4.setVisibility(View.GONE);
                    error5.setVisibility(View.GONE);
                    error6.setVisibility(View.GONE);
                    error7.setVisibility(View.GONE);
                    error8.setVisibility(View.GONE);
                }
                else if(!Email.matches(emailPattern))
                {
                    error1.setVisibility(View.GONE);
                    error2.setVisibility(View.GONE);
                    error3.setVisibility(View.GONE);
                    error4.setVisibility(View.VISIBLE);
                    error4.setText("enter a valid email address");
                    error5.setVisibility(View.GONE);
                    error6.setVisibility(View.GONE);
                    error7.setVisibility(View.GONE);
                    error8.setVisibility(View.GONE);
                }

                else{
                    error1.setVisibility(View.GONE);
                    error2.setVisibility(View.GONE);
                    error3.setVisibility(View.GONE);
                    error4.setVisibility(View.GONE);
                    error5.setVisibility(View.GONE);
                    error6.setVisibility(View.GONE);
                    error7.setVisibility(View.GONE);
                    error8.setVisibility(View.GONE);

                    String URL = "https://irms.in/api/add_lead";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("leeaddd", response);

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String Success = jsonObject.getString("status");
                                String msg = jsonObject.getString("msg");

                                if(Success.equals("200")){
                                    Toast.makeText(LeadInformationActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LeadInformationActivity.this,LeadActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(LeadInformationActivity.this, msg, Toast.LENGTH_SHORT).show();
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

                            params.put("first_name",first.getText().toString());
                            params.put("last_name",last.getText().toString());
                            params.put("mobile",mobile.getText().toString());
                            params.put("email",email.getText().toString());
                            params.put("main_category_name",Property);
                            params.put("region_name",Region);
                            params.put("budget",budget.getText().toString());
                            params.put("requirement",other.getText().toString());
                            Log.i("arrayyyyy", params.toString());
                            return params;

                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> headers = new HashMap<String, String>();
                            headers.put("Accept", "application/json");
                            headers.put("Authorization", "Bearer " + PreferenceUtils.getToken(LeadInformationActivity.this));

                            return headers;
                        }
                    };

                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            10000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    RequestQueue requestQueue = Volley.newRequestQueue(LeadInformationActivity.this);
                    requestQueue.add(stringRequest);


                }

            }
        });

    }


}