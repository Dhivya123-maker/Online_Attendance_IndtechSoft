package com.example.online_attendance_indtechsoft.LeadAllocation;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ParseException;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import com.example.online_attendance_indtechsoft.DashboardActivity;
import com.example.online_attendance_indtechsoft.History.HistoryModel;
import com.example.online_attendance_indtechsoft.Leads.LeadActivity;
import com.example.online_attendance_indtechsoft.R;
import com.example.online_attendance_indtechsoft.Task.TaskAssignActivity;
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
import java.util.Map;

public class LeadAllocationAdapter extends RecyclerView.Adapter<com.example.online_attendance_indtechsoft.LeadAllocation.LeadAllocationAdapter.ViewHolder> {

    List<LeadAllocationModel> leadAllocationModelList;
    Context context;
    private static final int REQUEST_CALL = 1;
    String call,Id,Wind;
    boolean isStarted = false;
    String time,time1;



    public LeadAllocationAdapter(Context context, List<LeadAllocationModel> leadAllocationModelList) {

        this.leadAllocationModelList = leadAllocationModelList;
        this.context = context;
    }


    @NonNull
    @Override
    public com.example.online_attendance_indtechsoft.LeadAllocation.LeadAllocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.lead_calls, parent, false);


        return new com.example.online_attendance_indtechsoft.LeadAllocation.LeadAllocationAdapter.ViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.online_attendance_indtechsoft.LeadAllocation.LeadAllocationAdapter.ViewHolder holder, final int position) {
        holder.number.setText(leadAllocationModelList.get(position).getMobile());
        holder.wind.setText(leadAllocationModelList.get(position).getWind());


    }

    @Override
    public int getItemCount() {
        return leadAllocationModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView number;
        ImageView call_img;
        TextView wind;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            number = itemView.findViewById(R.id.number);
            call_img = itemView.findViewById(R.id.call_img);
            wind = itemView.findViewById(R.id.wind_call);


            call_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss aa");
                    time = format.format(calendar.getTime());

                    int position = getAdapterPosition();
                    call = leadAllocationModelList.get(position).getMobile();
                    Id = leadAllocationModelList.get(position).getId();

                    call_img.setVisibility(View.GONE);
                    wind.setVisibility(View.VISIBLE);

                    makePhoneCall();


                    wind.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            wind.setVisibility(View.GONE);
                            call_img.setVisibility(View.VISIBLE);
                            Calendar calendar1 = Calendar.getInstance();
                            SimpleDateFormat format1 = new SimpleDateFormat("hh:mm:ss aa");
                            time1 = format1.format(calendar1.getTime());

                            String URL = "https://irms.in/api/lead_allocation";
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("responseeeeeeeee", response);

                                    try {
                                        JSONObject jsonObject = new JSONObject(response);

                                        String Success = jsonObject.getString("status");
                                        String msg = jsonObject.getString("msg");

                                        if (Success.equals("200")) {
                                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

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


                                    params.put("lead_id",Id);
                                    params.put("start_time",time);
                                    params.put("end_time",time1);
                                    params.put("note","call");

                                    Log.i("arrayyyyy", params.toString());
                                    return params;

                                }

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String, String> headers = new HashMap<String, String>();
                                    headers.put("Accept", "application/json");
                                    headers.put("Authorization", "Bearer " + PreferenceUtils.getToken(context));

                                    return headers;
                                }
                            };

                            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                    10000,
                                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                            RequestQueue requestQueue = Volley.newRequestQueue(context);
                            requestQueue.add(stringRequest);
                        }
                    });


                }
            });


        }


    }
    private void makePhoneCall() {
        String number = call;

        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));

            }

        }
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_CALL) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                makePhoneCall();
//            } else {
//                Toast.makeText(context, "Permission Denied to make a call" + "", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

}