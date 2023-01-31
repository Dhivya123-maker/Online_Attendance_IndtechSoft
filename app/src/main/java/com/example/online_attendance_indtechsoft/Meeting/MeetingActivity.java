package com.example.online_attendance_indtechsoft.Meeting;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MeetingActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    LinearLayout add_lnr;
    int start_hour,start_minute,end_hour,end_minute;
    Calendar calendar;
    ImageView back;
    ArrayList<String> date_array = new ArrayList<>();
    ArrayList<String> start_array = new ArrayList<>();
    ArrayList<String> end_array = new ArrayList<>();
    ArrayList<String> desc_array = new ArrayList<>();
    ArrayList<String> invite_array = new ArrayList<>();
    EditText date_relative,start_relative,end_relative;
//    TextView date,start,end;
    EditText description;
    LinearLayout remove_lnr,circle_add;
    EditText name;
    Button submit;
    String store_invite_array = "";
    String invite_arraylist = "";
    String m_invite_arraylist = "";
    LinearLayout name_lnr;
//    ImageView delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);

        linearLayout = (LinearLayout) findViewById(R.id.lnr);
        add_lnr = findViewById(R.id.add_lnr);
        back = findViewById(R.id.back);
        submit = findViewById(R.id.meeting_sumbit);

        submit.setVisibility(GONE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        add_lnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addView();
                submit.setVisibility(VISIBLE);

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_meeting();

            }
        });



    }
    public boolean checkIfValidAndRead() {
        date_array.clear();
        start_array.clear();
        end_array.clear();
        desc_array.clear();
        invite_array.clear();

        boolean result = true;

        for(int i=0;i<linearLayout.getChildCount();i++){

            View entry = linearLayout.getChildAt(i);

            date_relative = entry.findViewById(R.id.date_relative);
           // start_relative =entry.findViewById(R.id.start_time_relative);
            end_relative =entry.findViewById(R.id.end_time_relative);
            description = entry.findViewById(R.id.description);
            remove_lnr = entry.findViewById(R.id.remove_lnr);
//            circle_add = entry.findViewById(R.id.circle_add);
//            edit_lnr = entry.findViewById(R.id.edit_lnr);
//            date =entry.findViewById(R.id.date_txt);
//            start =entry.findViewById(R.id.start_time_txt);
//            end =entry.findViewById(R.id.end_time_txt);
            name = entry.findViewById(R.id.edit_name);


            if(!date_relative.getText().toString().equals("")){

                date_array.add(date_relative.getText().toString());


            }else {
                result = false;
                Toast.makeText(this, "date field is required", Toast.LENGTH_SHORT).show();

                break;
            }
            if(!start_relative.getText().toString().equals("")){
                start_array.add(start_relative.getText().toString());

            }else {
                result = false;
                Toast.makeText(this, "start time field is required", Toast.LENGTH_SHORT).show();

                break;
            }

            if(!end_relative.getText().toString().equals("")){
                end_array.add(end_relative.getText().toString());


            }else {
                result = false;
                Toast.makeText(this, "end time field is required", Toast.LENGTH_SHORT).show();

                break;
            }

            if(!description.getText().toString().equals("")){
                desc_array.add(description.getText().toString());


            }else {
                result = false;
                Toast.makeText(this, "description field is required", Toast.LENGTH_SHORT).show();
                break;
            }



            if(!name.getText().toString().equals("")){
                invite_array.add(name.getText().toString());


            }else {
                result = false;
                Toast.makeText(this, "invite field is required", Toast.LENGTH_SHORT).show();

                break;
            }



//                if(invite_array.size()<=1){
//                    invite_arraylist = invite_array.get(0);
//
//                }
//                else {
//                    invite_arraylist=  invite_array.toString().replace("[", "").replace("]", "");
////                    invite_array.clear();
//                }
            // m_invite_arraylist = m_invite_arraylist+invite_arraylist;
        }


        return result;


    }


    public void addView() {

       View add = getLayoutInflater().inflate(R.layout.meeting_entries,null,false);

        date_relative = add.findViewById(R.id.date_relative);
        start_relative =add.findViewById(R.id.start_time_relative);
        end_relative =add.findViewById(R.id.end_time_relative);
        description = add.findViewById(R.id.description);
        remove_lnr = add.findViewById(R.id.remove_lnr);
//        circle_add = add.findViewById(R.id.circle_add);
//        edit_lnr = add.findViewById(R.id.edit_lnr);
//        date =add.findViewById(R.id.date_txt);
//        start =add.findViewById(R.id.start_time_txt);
//        end =add.findViewById(R.id.end_time_txt);
        name = add.findViewById(R.id.edit_name);
//        name_lnr =add. findViewById(R.id.name_lnr);
//        delete = add.findViewById(R.id.delete);


//        circle_add.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("ResourceAsColor")
//            @Override
//            public void onClick(View view) {
//                name_lnr.setVisibility(VISIBLE);
//                circle_add.setVisibility(GONE);
//
//                delete.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Dialog dialog;
//
//                        dialog = new Dialog(MeetingActivity.this);
//
//                        // set custom dialog
//                        dialog.setContentView(R.layout.alert_dailogbox);
//
//                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//
//                        // set transparent background
//                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//                        // show dialog
//                        dialog.show();
//
//                        // Initialize and assign variable
//                        TextView title = dialog.findViewById(R.id.title);
//                        RelativeLayout no = dialog.findViewById(R.id.no);
//                        RelativeLayout yes = dialog.findViewById(R.id.yes);
//
//                        title.setText("Are you sure want to remove this name");
//
//
//                        no.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.dismiss();
//                            }
//                        });
//
//                        yes.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                dialog.dismiss();
//                                name_lnr.setVisibility(GONE);
//                                circle_add.setVisibility(VISIBLE);
//
//
//
//                            }
//                        });
//                    }
//                });
//
//
////                  edit = getLayoutInflater().inflate(R.layout.edittext_layout,null,false);
////
////                  name = edit.findViewById(R.id.name);
////                  delete = edit.findViewById(R.id.delete);
////
////
////                      delete.setOnClickListener(new View.OnClickListener() {
////                          @Override
////                          public void onClick(View view) {
////
////
////                              Dialog dialog;
////
////                              dialog = new Dialog(MeetingActivity.this);
////
////                              // set custom dialog
////                              dialog.setContentView(R.layout.alert_dailogbox);
////
////                              dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
////
////                              // set transparent background
////                              dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
////
////                              // show dialog
////                              dialog.show();
////
////                              // Initialize and assign variable
////                              TextView title = dialog.findViewById(R.id.title);
////                              RelativeLayout no = dialog.findViewById(R.id.no);
////                              RelativeLayout yes = dialog.findViewById(R.id.yes);
////
////                              title.setText("Are you sure want to remove this name");
////
////
////                              no.setOnClickListener(new View.OnClickListener() {
////                                  @Override
////                                  public void onClick(View v) {
////
////                                      dialog.dismiss();
////                                  }
////                              });
////
////                              yes.setOnClickListener(new View.OnClickListener() {
////                                  @Override
////                                  public void onClick(View view) {
////
////                                      dialog.dismiss();
////                                      remove(edit);
////
////                                  }
////                              });
////
////                          }
////                      });
////
////
////                      edit_lnr.addView(edit);
//
//
//
//            }
//        });



//        date_relative.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                calendar = Calendar.getInstance();
//
//                final DatePickerDialog.OnDateSetListener dateA = new DatePickerDialog.OnDateSetListener() {
//
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                          int dayOfMonth) {
//
//                        calendar.set(Calendar.YEAR, year);
//                        calendar.set(Calendar.MONTH, monthOfYear);
//                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//
//                        date_relative.setText(dayOfMonth+"-"+(monthOfYear+1)+"-"+year);
//
//
//                    }
//                };
//
//                new DatePickerDialog(MeetingActivity.this, dateA, calendar.get(Calendar.YEAR),
//                        calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
//
//        start_relative.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                TimePickerDialog timePickerDialog = new TimePickerDialog(MeetingActivity.this,
//                        new TimePickerDialog.OnTimeSetListener() {
//                            @Override
//                            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
//
//                                start_hour = hour;
//                                start_minute = minute;
//                                Calendar calendar = Calendar.getInstance();
//                                calendar.set(0,0,0,start_hour,start_minute);
//                                start_relative.setText(DateFormat.format("hh:mm aa",calendar));
//
//
//
//                            }
//                        },12,0,false);
//
//                timePickerDialog.updateTime(start_hour,start_minute);
//                timePickerDialog.show();
//            }
//        });
//
//        end_relative.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                TimePickerDialog timePickerDialog = new TimePickerDialog(MeetingActivity.this,
//                        new TimePickerDialog.OnTimeSetListener() {
//                            @Override
//                            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
//
//                                end_hour = hour;
//                                end_minute = minute;
//                                Calendar calendar = Calendar.getInstance();
//                                calendar.set(0,0,0,end_hour,end_minute);
//                                end_relative.setText(DateFormat.format("hh:mm aa",calendar));
//
//
//
//                            }
//                        },12,0,false);
//
//                timePickerDialog.updateTime(end_hour,end_minute);
//                timePickerDialog.show();
//            }
//        });



        remove_lnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog;

                dialog = new Dialog(MeetingActivity.this);

                // set custom dialog
                dialog.setContentView(R.layout.alert_dailogbox);

                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                TextView title = dialog.findViewById(R.id.title);
                RelativeLayout no = dialog.findViewById(R.id.no);
                RelativeLayout yes = dialog.findViewById(R.id.yes);

                title.setText("Are you sure want to remove this schedule");


                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                        removeView(add);
                        delete_meeting();
                        add_lnr.setVisibility(VISIBLE);

                    }
                });


            }
        });

        linearLayout.addView(add);

    }

    public void removeView(View view){

        linearLayout.removeView(view);

    }

    public  void delete_meeting(){
        String URL = "https://irms.in/api/meeting_delete";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("000000000",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String Success = jsonObject.getString("status");
                    String msg = jsonObject.getString("msg");

                    if(Success.equals("200")){
                        if(linearLayout.getChildCount()<1){
                            submit.setVisibility(GONE);
                        }else{
                            submit.setVisibility(View.VISIBLE);
                        }
                        Toast.makeText(MeetingActivity.this, msg, Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(MeetingActivity.this, msg, Toast.LENGTH_SHORT).show();
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
                headers.put("Accept","application/json");
                headers.put("Authorization", "Bearer " + PreferenceUtils.getToken(MeetingActivity.this));

                return headers;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(MeetingActivity.this);
        requestQueue.add(stringRequest);


    }
    public void add_meeting() {
        if (checkIfValidAndRead()) {
            String URL = "https://irms.in/api/add_meeting";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("000000000", response);

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        String Success = jsonObject.getString("status");
                        String msg = jsonObject.getString("msg");


                        if(!date_relative.getText().toString().matches("\\d{4}-[01]\\d-[0-3]\\d")){
                            Toast.makeText(MeetingActivity.this, "date should be in date pattern", Toast.LENGTH_SHORT).show();
                        }

                        else
                            if(!start_relative.getText().toString().matches("(1[012]|[1-9]):" + "[0-5][0-9](\\s)" + "?(?i)(am|pm)")){
                            Toast.makeText(MeetingActivity.this, "start time should be in time pattern", Toast.LENGTH_SHORT).show();

                            }
                            else
                            if(!end_relative.getText().toString().matches("(1[012]|[1-9]):" + "[0-5][0-9](\\s)" + "?(?i)(am|pm)")){
                                Toast.makeText(MeetingActivity.this, "end time should be in time pattern", Toast.LENGTH_SHORT).show();

                            }
                         else if(Success.equals("200")) {
                            Toast.makeText(MeetingActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MeetingActivity.this, DashboardActivity.class);
                            startActivity(intent);
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

                    for (int i = 0; i < linearLayout.getChildCount(); i++) {
                        params.put("create_date[" + i + "]", date_array.get(i));
                        params.put("start_time[" + i + "]", start_array.get(i));
                        params.put("end_time[" + i + "]", end_array.get(i));
                        params.put("description[" + i + "]", desc_array.get(i));
                        params.put("invite_people[" + i + "]", invite_array.get(i));

                    }

                    Log.i("arrayyyyy", params.toString());
                    return params;

                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", "Bearer " + PreferenceUtils.getToken(MeetingActivity.this));

                    return headers;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            RequestQueue requestQueue = Volley.newRequestQueue(MeetingActivity.this);
            requestQueue.add(stringRequest);


        }
    }

}