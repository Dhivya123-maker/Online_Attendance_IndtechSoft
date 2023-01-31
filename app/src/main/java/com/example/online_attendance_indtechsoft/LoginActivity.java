package com.example.online_attendance_indtechsoft;

import static android.view.View.GONE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.online_attendance_indtechsoft.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Button login_btn;
    EditText username,password;
    TextView username_error,password_error;
    String user_id,token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitylogin);


        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        username_error = findViewById(R.id.error1);
        password_error = findViewById(R.id.error2);


        login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().isEmpty()){
                    username_error.setVisibility(View.VISIBLE);
                    username_error.setText("username filed is required");
                    password_error.setVisibility(GONE);
                }
                else if(password.getText().toString().isEmpty()){
                    password_error.setVisibility(View.VISIBLE);
                    password_error.setText("password filed is required");
                    username_error.setVisibility(GONE);
                }
                else{
                    login();
                }

            }
        });
    }
    public void onBackPressed() {

        Dialog dialog;

        dialog=new Dialog(LoginActivity.this);

        // set custom dialog
        dialog.setContentView(R.layout.alert_dailogbox);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        // set transparent background
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // show dialog
        dialog.show();

        // Initialize and assign variable
        TextView title=dialog.findViewById(R.id.title);
        RelativeLayout no=dialog.findViewById(R.id.no);
        RelativeLayout yes=dialog.findViewById(R.id.yes);

        title.setText("Are you sure want to exit");

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finishAffinity();
            }
        });

    }
    public void login() {
        String URL = "https://www.irms.in/api/user_login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("000000000",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String Success = jsonObject.getString("status");
                    String msg = jsonObject.getString("msg");

                    if(Success.equals("200")){
                        JSONObject records = jsonObject.getJSONObject("records");
                        user_id = records.getString("user_id");
                        token = records.getString("token");

                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                       Intent intent = new Intent(LoginActivity.this,DashboardActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("user_id", user_id);
                        intent.putExtra("token", token);
                        PreferenceUtils.saveid(user_id, LoginActivity.this);
                        PreferenceUtils.saveToken(token, LoginActivity.this);
                        startActivity(intent);


                    }else {
                        password_error.setVisibility(View.VISIBLE);
                        password_error.setText(msg);
                        username_error.setVisibility(GONE);

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

                params.put("username",username.getText().toString());
                params.put("password",password.getText().toString());
                Log.i("arrayyyyy",params.toString());
                return params;

            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept","application/json");

                return headers;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(stringRequest);



    }
}