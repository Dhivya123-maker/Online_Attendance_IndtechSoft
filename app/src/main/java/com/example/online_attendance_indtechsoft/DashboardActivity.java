package com.example.online_attendance_indtechsoft;

import static android.view.View.GONE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.online_attendance_indtechsoft.History.HistoryActivity;
import com.example.online_attendance_indtechsoft.Leads.LeadActivity;
import com.example.online_attendance_indtechsoft.Notification.NotificationActivity;
import com.example.online_attendance_indtechsoft.Task.TaskAssignActivity;
import com.example.online_attendance_indtechsoft.Task.ViewTaskActivity;
import com.example.online_attendance_indtechsoft.utils.PreferenceUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import EmployeeAttendance.EmployeeAttendanceActivity;
import EmployeeInfo.EmployeeInfoActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.Date;


public class DashboardActivity extends AppCompatActivity {

    LinearLayout day_in,day_out,check_in, check_out,meeting_lnr,location_history;
    LinearLayout lunch,tea,travel,meeting,break_time,leave,permission;
    private boolean isblue = true;
//    Button submit;
    LinearLayout logout, notification;
    private Geocoder geocoder;
    FloatingActionButton image_upload;
    int CAMERA_REQUEST1 = 1;
    int SELECT_PHOTO = 2;
    String currentphoto;
    Bitmap bitmap;
    CircleImageView avatar;
    LinearLayout location_lnr,capture_lnr,task_lnr;
    int resId;
    TextView location_txt,headline;
    public LocationManager locationManager;
    public LocationListener locationListener = new MyLocationListener();
    String lat, longi;
    private boolean gps_enable = false;
    private boolean network_enable = false;
    List<Address> myaddress;
    Drawable d_lunch,d_tea,d_travel,d_meeting,d_break,d_leave,d_permission;
    LinearLayout saved_location;

    TextView home_txt;
    LinearLayout task_schedule_lnr,employee_info,employee_attendance_list;
    Dialog dialog;
    NavigationView navigationView;
    ImageView menu;
    String user_id,token;
    String currentTime;
    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView break_time_txt,profile_name;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);

        navigationView = findViewById(R.id.nav_view);
        day_in = findViewById(R.id.intime_btn);
        day_out = findViewById(R.id.outime_btn);
        logout = findViewById(R.id.logout_lnr);
        notification = findViewById(R.id.notification);
        check_in = findViewById(R.id.checkin_btn);
        check_out = findViewById(R.id.checkout_btn);
        image_upload = findViewById(R.id.upload_img);
        avatar = findViewById(R.id.avatar);
        location_lnr = findViewById(R.id.location_lnr);
        location_txt = findViewById(R.id.location_txt);
        capture_lnr = findViewById(R.id.capture_lnr);
        meeting_lnr = findViewById(R.id.meeting_lnr);
        task_lnr = findViewById(R.id.task_lnr);
        headline = findViewById(R.id.headline);
        task_schedule_lnr = findViewById(R.id.task_schedule_lnr);
        location_history = findViewById(R.id.location_history);
        employee_info = findViewById(R.id.employee_info);
        employee_attendance_list = findViewById(R.id.employee_attedance_list);
        profile_name = findViewById(R.id.profile_name);


        saved_location = findViewById(R.id.saved_location);

        home_txt = findViewById(R.id.home_txt);
        menu = findViewById(R.id.menu);
        break_time_txt = findViewById(R.id.break_time_txt);


        saved_location.setVisibility(View.GONE);

        lunch = findViewById(R.id.lunch);
        tea = findViewById(R.id.tea);
        meeting = findViewById(R.id.meeting);
        break_time = findViewById(R.id.break_time);
        travel = findViewById(R.id.travel);
        leave = findViewById(R.id.leave);
        permission = findViewById(R.id.permission);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        user_id = intent.getStringExtra("user_id");


        profile_name.setVisibility(GONE);
        getBitmap();

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        checklocationpermission();

        DrawerLayout drawerLayout = findViewById(R.id.drawerr_layout);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(DashboardActivity.this,
                drawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                switch (id) {

                    case R.id.Lead:
                        Intent home = new Intent(DashboardActivity.this, LeadActivity.class);
                        home.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(home);
                        break;

                }
                return true;

            }

        });



        employee_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, EmployeeInfoActivity.class));

            }
        });
        employee_attendance_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, EmployeeAttendanceActivity.class));

            }
        });
        location_history.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("HardwareIds")
            @Override
            public void onClick(View view) {

               startActivity(new Intent(DashboardActivity.this, HistoryActivity.class));
            }
        });

        task_schedule_lnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this,TaskAssignActivity.class);
                intent.putExtra("update","task");
                startActivity(intent);
            }
        });

        task_lnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, ViewTaskActivity.class));

            }
        });

        meeting_lnr.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, com.example.online_attendance_indtechsoft.Meeting.MeetingActivity.class));
            }
        });

        capture_lnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, com.example.online_attendance_indtechsoft.ImageCaptureActivity.class));
            }
        });


        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialog_box();

            }
        });
        tea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog_box();

            }
        });
        meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_box();

            }
        });
        travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_box();
            }
        });
        break_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_box();

            }
        });
        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_box();


            }
        });

        permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_box();


            }
        });


        image_upload.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                BottomSheetDialog dialog = new BottomSheetDialog(DashboardActivity.this);

                dialog.setContentView(R.layout.choose_image);

                TextView close = dialog.findViewById(R.id.close);
                TextView file = dialog.findViewById(R.id.file);
                TextView camera = dialog.findViewById(R.id.camera);


                file.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, SELECT_PHOTO);
                    }
                });


                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                        String filename = "Photo";
                        File storagedirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                        try {
                            File imagefile = File.createTempFile(filename,".jpg",storagedirectory);
                            currentphoto = imagefile.getAbsolutePath();
                            Uri imageuri = FileProvider.getUriForFile(DashboardActivity.this,"com.example.online_attendance_indtechsoft.provider",imagefile);

                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageuri);
                            startActivityForResult(intent,CAMERA_REQUEST1);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });


                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


                dialog.setCancelable(false);

                dialog.show();
            }
        });


        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, NotificationActivity.class));

            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog;

                dialog = new Dialog(DashboardActivity.this);

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

                title.setText("Are you sure want to logout");


                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String URL = "https://www.irms.in/api/user_logout";
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("responseeeeeeeee", response);

                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    String Success = jsonObject.getString("status");
                                    String msg = jsonObject.getString("msg");

                                    if (Success.equals("200")) {
                                        PreferenceUtils.saveid(null, DashboardActivity.this);
                                        PreferenceUtils.saveToken(null, DashboardActivity.this);
                                        Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(DashboardActivity.this, LoginActivity.class));

                                    } else {
                                        Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

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
                                headers.put("Authorization", "Bearer "+ PreferenceUtils.getToken(DashboardActivity.this));

                                return headers;
                            }
                        };

                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                10000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        RequestQueue requestQueue = Volley.newRequestQueue(DashboardActivity.this);
                        requestQueue.add(stringRequest);
                    }
                });


            }
        });
        day_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String URL = "https://www.irms.in/api/day_in_out";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("responseeeeee", response);

                        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String Success = jsonObject.getString("status");
                            String msg = jsonObject.getString("msg");

                            if (Success.equals("200")) {

                                dialog = new Dialog(DashboardActivity.this);

                                // set custom dialog
                                dialog.setContentView(R.layout.time_popup);

                                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                                // set transparent background
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                // show dialog
                                dialog.show();

                                TextView time_txt = dialog.findViewById(R.id.time_txt);
                                TextView late_time = dialog.findViewById(R.id.late_time);
                                RelativeLayout yes = dialog.findViewById(R.id.ok);

                                yes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });

                                time_txt.setText("Your InTime is : "+currentTime);
                                late_time.setText(msg);


                            } else {
                                Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

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

                        params.put("time", new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date())+" am");
                        params.put("type", "day_in");

                        Log.i("arrayyyyy", params.toString());
                        return params;

                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("Accept", "application/json");
                        headers.put("Authorization", "Bearer "+ PreferenceUtils.getToken(DashboardActivity.this));

                        return headers;
                    }
                };

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                RequestQueue requestQueue = Volley.newRequestQueue(DashboardActivity.this);
                requestQueue.add(stringRequest);


            }
        });

        day_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String URL = "https://www.irms.in/api/day_in_out";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("responseeeeeeeee", response);

                        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String Success = jsonObject.getString("status");
                            String msg = jsonObject.getString("msg");

                            if (Success.equals("200")) {

                                dialog = new Dialog(DashboardActivity.this);

                                // set custom dialog
                                dialog.setContentView(R.layout.time_popup);

                                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                                // set transparent background
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                // show dialog
                                dialog.show();

                                TextView time_txt = dialog.findViewById(R.id.time_txt);
                                TextView late_time = dialog.findViewById(R.id.late_time);
                                RelativeLayout yes = dialog.findViewById(R.id.ok);

                                yes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        dialog.dismiss();
                                    }
                                });

                                time_txt.setText("Your OutTime is : "+currentTime);

                                late_time.setText("Log off");

                            } else {
                               Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

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

                        params.put("time", new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date())+" am");
                        params.put("type", "day_out");


                        Log.i("arrayyyyy", params.toString());
                        return params;

                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("Accept", "application/json");
                        headers.put("Authorization", "Bearer "+ PreferenceUtils.getToken(DashboardActivity.this));

                        return headers;
                    }
                };

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                RequestQueue requestQueue = Volley.newRequestQueue(DashboardActivity.this);
                requestQueue.add(stringRequest);


            }
        });

        check_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    gps_enable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                } catch (Exception ex) {

                }
                try {
                    network_enable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                } catch (Exception ex) {

                }

                if (!gps_enable && !network_enable) {

                    buildAlertMessageNoGps();
                }

                if (gps_enable) {
                    if (ActivityCompat.checkSelfPermission(DashboardActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(DashboardActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                }

                if(network_enable){
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

                    dialog = new Dialog(DashboardActivity.this);

                    // set custom dialog
                    dialog.setContentView(R.layout.location_dialog_box);

                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                    // set transparent background
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    // show dialog
                    dialog.show();
                    RadioButton home,work,other;
                    EditText address_edit;
                    RelativeLayout cancel,ok;

                   cancel = dialog.findViewById(R.id.no);
                   ok = dialog.findViewById(R.id.yes);
                    radioGroup =dialog.findViewById(R.id.radioGroup);
                    address_edit = dialog.findViewById(R.id.address_edit);
                    home = dialog.findViewById(R.id.home_radio);
                    work = dialog.findViewById(R.id.office_radio);
                    other = dialog.findViewById(R.id.other_radio);


                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    other.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            address_edit.setVisibility(View.VISIBLE);


                        }
                    });

                    home.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            address_edit.setVisibility(View.GONE);
                        }
                    });
                    work.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            address_edit.setVisibility(View.GONE);
                        }
                    });
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            check_btn();
                            String Address = address_edit.getText().toString();

                            if(!home.isChecked() && !work.isChecked() && !other.isChecked()){
                                Toast.makeText(DashboardActivity.this, "Please select one choice", Toast.LENGTH_SHORT).show();
                            }
                            else if(home.isChecked()){
                                home_txt.setText("Home");
                                saved_location.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                                check_in();
                            }
                            else if(work.isChecked()){
                                home_txt.setText("Office");
                                saved_location.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                                check_in();
                            }

                            else  if(Address.isEmpty() && address_edit.getVisibility()==View.VISIBLE){
                                Toast.makeText(DashboardActivity.this, "Please enter location name", Toast.LENGTH_SHORT).show();
                                home_txt.setText("Other");
                                saved_location.setVisibility(View.VISIBLE);
                            }else {
                                home_txt.setText(address_edit.getText().toString());
                                saved_location.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                                check_in();
                            }

                        }
                    });

                }


            }
        });

        check_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    gps_enable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                } catch (Exception ex) {

                }
                try {
                    network_enable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                } catch (Exception ex) {

                }

                if (!gps_enable && !network_enable) {

                    buildAlertMessageNoGps();
                }

                if (gps_enable) {
                    if (ActivityCompat.checkSelfPermission(DashboardActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(DashboardActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                }


                if(network_enable){
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

                    dialog = new Dialog(DashboardActivity.this);

                    // set custom dialog
                    dialog.setContentView(R.layout.location_dialog_box);

                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                    // set transparent background
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    // show dialog
                    dialog.show();
                    RadioButton home,work,other;
                    EditText address_edit;
                    RelativeLayout cancel,ok;

                    cancel = dialog.findViewById(R.id.no);
                    ok = dialog.findViewById(R.id.yes);
                    radioGroup =dialog.findViewById(R.id.radioGroup);
                    address_edit = dialog.findViewById(R.id.address_edit);
                    home = dialog.findViewById(R.id.home_radio);
                    work = dialog.findViewById(R.id.office_radio);
                    other = dialog.findViewById(R.id.other_radio);


                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    other.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            address_edit.setVisibility(View.VISIBLE);


                        }
                    });


                    home.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            address_edit.setVisibility(View.GONE);
                        }
                    });
                    work.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            address_edit.setVisibility(View.GONE);
                        }
                    });
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            check_btn();
                            String Address = address_edit.getText().toString();

                            if(!home.isChecked() && !work.isChecked() && !other.isChecked()){
                                Toast.makeText(DashboardActivity.this, "Please select one choice", Toast.LENGTH_SHORT).show();
                            }
                            else if(home.isChecked()){
                                home_txt.setText("Home");
                                saved_location.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                                check_out();
                            }
                            else if(work.isChecked()){
                                home_txt.setText("Office");
                                saved_location.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                                check_out();
                            }

                            else  if(Address.isEmpty() && address_edit.getVisibility()==View.VISIBLE){
                                Toast.makeText(DashboardActivity.this, "Please enter location name", Toast.LENGTH_SHORT).show();
                                home_txt.setText("Other");
                                saved_location.setVisibility(View.VISIBLE);
                            }else {
                                home_txt.setText(address_edit.getText().toString());
                                saved_location.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                                check_out();
                            }

                        }
                    });

                }

            }
        });



    }


    @SuppressLint({"ResourceType", "UseCompatLoadingForDrawables"})
    public void dialog_box( ){

        Dialog dialog;

        dialog = new Dialog(DashboardActivity.this);

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


        d_lunch = lunch.getBackground();
        d_tea = tea.getBackground();
        d_travel = travel.getBackground();
        d_meeting = meeting.getBackground();
        d_break = break_time.getBackground();
        d_leave = leave.getBackground();
        d_permission = permission.getBackground();



        if(lunch.isPressed()){
            if(d_tea.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable tea time", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
            else if(d_travel.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable travel mode", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_meeting.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable meeting time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_break.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable break time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_leave.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable leave option", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_permission.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable permission option", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
                else{
                title.setText("Are you sure want to enable lunch time");
            }


        }

        else if(tea.isPressed() ) {


            if(d_lunch.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable lunch time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_travel.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable travel mode", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_meeting.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable meeting time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_break.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable break time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_leave.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable leave option", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_permission.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable permission option", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else{
                title.setText("Are you sure want to enable tea time");
            }



        }

        else if(travel.isPressed()){
            if(d_lunch.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable lunch time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_tea.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable tea time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_meeting.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable meeting time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_break.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable break time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_leave.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable leave option", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_permission.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable permission option", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else{
                title.setText("Are you sure want to enable travel mode");
            }

        }
        else if(meeting.isPressed() ){
            if(d_lunch.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable lunch time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_tea.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable tea mode", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_travel.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable travel mode", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_break.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable break time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_leave.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable leave option", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_permission.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable permission option", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else{
                title.setText("Are you sure want to enable meeting time");
            }

        }
        else if(break_time.isPressed()){
            if(d_lunch.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable lunch time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_tea.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable tea time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_travel.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable travel mode", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_meeting.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable meeting time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_leave.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable leave option", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_permission.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable permission option", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else{
                title.setText("Are you sure want to enable break time");
            }

        }
        else if(leave.isPressed() ){

          if(d_lunch.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable lunch time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_tea.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable tea time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_travel.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable travel mode", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_meeting.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable meeting time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_break.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable break time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_permission.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable permission option", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else{
                title.setText("Are you sure want to enable leave option");
            }
        }
        else if(permission.isPressed()){
            if(d_lunch.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable lunch time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_tea.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable tea time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_travel.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable travel mode", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_meeting.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable meeting time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_break.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable break time", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else if(d_leave.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
                Toast.makeText(this, "Please disable leave option", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
            else{
                title.setText("Are you sure want to enable permission option");
            }
        }




        if(lunch.isPressed() && d_lunch.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
            title.setText("Are you sure want to disable lunch time");

        }

        if(tea.isPressed()  && d_tea.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
            title.setText("Are you sure want to disable tea time");
        }

        else if(travel.isPressed() && d_travel.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
            title.setText("Are you sure want to disable travel mode");


        }

        else if(meeting.isPressed()  && d_meeting.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
            title.setText("Are you sure want to disable meeting time");


        }

        else if(break_time.isPressed()  && d_break.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
            title.setText("Are you sure want to disable break time");

        }
        else if(leave.isPressed()  && d_leave.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
            title.setText("Are you sure want to disable leave option");

        }

        else if(permission.isPressed() && d_permission.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()){
            title.setText("Are you sure want to disable permission option");

        }



        no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                    if(lunch.isPressed() && title.getText().equals("Are you sure want to enable lunch time") && resId==R.drawable.blue_circle){
                        isblue = !isblue;
                        resId = isblue ? R.drawable.blue_circle : R.drawable.peacockblue_circle;
                        lunch.setBackgroundResource(resId);



                    }
                   else if(lunch.isPressed() && title.getText().equals("Are you sure want to disable lunch time")  && resId==R.drawable.peacockblue_circle){
                        isblue = !isblue;
                        resId = isblue ? R.drawable.peacockblue_circle : R.drawable.blue_circle;
                        lunch.setBackgroundResource(resId);



                    }
                    if(tea.isPressed() && title.getText().equals("Are you sure want to enable tea time") && resId==R.drawable.blue_circle){
                        isblue = !isblue;
                        resId = isblue ? R.drawable.blue_circle : R.drawable.peacockblue_circle;
                        tea.setBackgroundResource(resId);



                    }
                    else if(tea.isPressed() && title.getText().equals("Are you sure want to disable tea time")  && resId==R.drawable.peacockblue_circle){
                        isblue = !isblue;
                        resId = isblue ? R.drawable.peacockblue_circle : R.drawable.blue_circle;
                        tea.setBackgroundResource(resId);


                    }
                    else if(travel.isPressed() && title.getText().equals("Are you sure want to enable travel mode") && resId==R.drawable.blue_circle){
                        isblue = !isblue;
                        resId = isblue ? R.drawable.blue_circle : R.drawable.peacockblue_circle;
                        travel.setBackgroundResource(resId);


                    }
                    else if(travel.isPressed() && title.getText().equals("Are you sure want to disable travel mode")  && resId==R.drawable.peacockblue_circle){
                        isblue = !isblue;
                        resId = isblue ? R.drawable.peacockblue_circle : R.drawable.blue_circle;
                        travel.setBackgroundResource(resId);

                    }
                    else if(meeting.isPressed() && title.getText().equals("Are you sure want to enable meeting time") && resId==R.drawable.blue_circle){
                        isblue = !isblue;
                        resId = isblue ? R.drawable.blue_circle : R.drawable.peacockblue_circle;
                        meeting.setBackgroundResource(resId);


                    }
                    else if(meeting.isPressed() && title.getText().equals("Are you sure want to disable meeting time")  && resId==R.drawable.peacockblue_circle){
                        isblue = !isblue;
                        resId = isblue ? R.drawable.peacockblue_circle : R.drawable.blue_circle;
                        meeting.setBackgroundResource(resId);

                    }
                   else if(break_time.isPressed() && title.getText().equals("Are you sure want to enable break time") && resId==R.drawable.blue_circle){
                        isblue = !isblue;
                        resId = isblue ? R.drawable.blue_circle : R.drawable.peacockblue_circle;
                        break_time.setBackgroundResource(resId);


                    }
                    else if(break_time.isPressed() && title.getText().equals("Are you sure want to disable break time")  && resId==R.drawable.peacockblue_circle){
                        isblue = !isblue;
                        resId = isblue ? R.drawable.peacockblue_circle : R.drawable.blue_circle;
                        meeting.setBackgroundResource(resId);

                    }
                    else if(leave.isPressed() && title.getText().equals("Are you sure want to enable leave option") && resId==R.drawable.blue_circle){
                        isblue = !isblue;
                        resId = isblue ? R.drawable.blue_circle : R.drawable.peacockblue_circle;
                        leave.setBackgroundResource(resId);


                    }
                    else if(leave.isPressed() && title.getText().equals("Are you sure want to disable leave option")  && resId==R.drawable.peacockblue_circle){
                        isblue = !isblue;
                        resId = isblue ? R.drawable.peacockblue_circle : R.drawable.blue_circle;
                        leave.setBackgroundResource(resId);

                    }
                    else if(permission.isPressed() && title.getText().equals("Are you sure want to enable permission option") && resId==R.drawable.blue_circle){
                        isblue = !isblue;
                        resId = isblue ? R.drawable.blue_circle : R.drawable.peacockblue_circle;
                        permission.setBackgroundResource(resId);


                    }
                    else if(permission.isPressed() && title.getText().equals("Are you sure want to disable permission option")  && resId==R.drawable.peacockblue_circle){
                        isblue = !isblue;
                        resId = isblue ? R.drawable.peacockblue_circle : R.drawable.blue_circle;
                        permission.setBackgroundResource(resId);

                    }

                }
            });





        if(lunch.isPressed()){

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                    break_time_txt.setVisibility(View.GONE);

                    if(d_lunch.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()) {
//                        Toast.makeText(DashboardActivity.this, "Lunch time disable", Toast.LENGTH_SHORT).show();
                        String URL = "https://www.irms.in/api/menu_break";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("responseeeeeeeee", response);
                                currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    String Success = jsonObject.getString("status");
                                    String msg = jsonObject.getString("msg");

                                    if (Success.equals("200")) {
                                        lunch.setBackgroundResource(R.drawable.blue_circle);
                                        Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();


                                    } else {
                                       Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

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

                                params.put("time",new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));
                                params.put("type","lunch");
                                params.put("mode", "disable");


                                Log.i("arrayyyyy", params.toString());
                                return params;

                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Accept", "application/json");
                                headers.put("Authorization", "Bearer " + PreferenceUtils.getToken(DashboardActivity.this));

                                return headers;
                            }
                        };

                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                10000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        RequestQueue requestQueue = Volley.newRequestQueue(DashboardActivity.this);
                        requestQueue.add(stringRequest);


                    }else{
//                        Toast.makeText(DashboardActivity.this, "lunch time enable", Toast.LENGTH_SHORT).show();
                        String URL = "https://www.irms.in/api/menu_break";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("responseeeeeeeee", response);
                                currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    String Success = jsonObject.getString("status");
                                    String msg = jsonObject.getString("msg");

                                    if (Success.equals("200")) {
                                        lunch.setBackgroundResource(R.drawable.peacockblue_circle);
                                        Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();
                                        currentTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());
                                        break_time_txt.setVisibility(View.VISIBLE);
                                        break_time_txt.setText("Lunch Time"+" ("+currentTime+")");

                                    } else {

                                    Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

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

                                params.put("time",new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));
                                params.put("type","lunch");
                                params.put("mode", "enable");


                                Log.i("arrayyyyy", params.toString());
                                return params;

                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Accept", "application/json");
                                headers.put("Authorization", "Bearer " + PreferenceUtils.getToken(DashboardActivity.this));

                                return headers;
                            }
                        };

                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                10000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        RequestQueue requestQueue = Volley.newRequestQueue(DashboardActivity.this);
                        requestQueue.add(stringRequest);

                    }

                }
            });

        }
        else if(tea.isPressed()){
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    break_time_txt.setVisibility(View.GONE);
                    if(d_tea.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()) {
                       // Toast.makeText(DashboardActivity.this, "Tea time enable", Toast.LENGTH_SHORT).show();
                        String URL = "https://www.irms.in/api/menu_break";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("responseeeeeeeee", response);
                                currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    String Success = jsonObject.getString("status");
                                    String msg = jsonObject.getString("msg");

                                    if (Success.equals("200")) {
                                        tea.setBackgroundResource(R.drawable.blue_circle);
                                        Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();


                                    } else {
                                       Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

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

                                params.put("time",new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));
                                params.put("type","tea");
                                params.put("mode", "disable");


                                Log.i("arrayyyyy", params.toString());
                                return params;

                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Accept", "application/json");
                                headers.put("Authorization", "Bearer " + PreferenceUtils.getToken(DashboardActivity.this));

                                return headers;
                            }
                        };

                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                10000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        RequestQueue requestQueue = Volley.newRequestQueue(DashboardActivity.this);
                        requestQueue.add(stringRequest);


                    }else{
                      //  Toast.makeText(DashboardActivity.this, "Already take tea break for the day", Toast.LENGTH_SHORT).show();
                        String URL = "https://www.irms.in/api/menu_break";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("responseeeeeeeee", response);
                                currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    String Success = jsonObject.getString("status");
                                    String msg = jsonObject.getString("msg");

                                    if (Success.equals("200")) {
                                        tea.setBackgroundResource(R.drawable.peacockblue_circle);
                                        Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();
                                        currentTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());
                                        break_time_txt.setVisibility(View.VISIBLE);
                                        break_time_txt.setText("Tea Time"+" ("+currentTime+")");

                                    } else {
                                        Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

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

                                params.put("time",new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));
                                params.put("type","tea");
                                params.put("mode", "enable");


                                Log.i("arrayyyyy", params.toString());
                                return params;

                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Accept", "application/json");
                                headers.put("Authorization", "Bearer " + PreferenceUtils.getToken(DashboardActivity.this));

                                return headers;
                            }
                        };

                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                10000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        RequestQueue requestQueue = Volley.newRequestQueue(DashboardActivity.this);
                        requestQueue.add(stringRequest);

                    }


                }
            });

        }
        else if(travel.isPressed()){
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    break_time_txt.setVisibility(View.GONE);
                    if(d_travel.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()) {
                        String URL = "https://www.irms.in/api/menu_break";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("responseeeeeeeee", response);
                                currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    String Success = jsonObject.getString("status");
                                    String msg = jsonObject.getString("msg");

                                    if (Success.equals("200")) {
                                        travel.setBackgroundResource(R.drawable.blue_circle);
                                        Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();


                                    } else {
                                        Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

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

                                params.put("time",new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));
                                params.put("type","travel");
                                params.put("mode", "disable");


                                Log.i("arrayyyyy", params.toString());
                                return params;

                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Accept", "application/json");
                                headers.put("Authorization", "Bearer " + PreferenceUtils.getToken(DashboardActivity.this));

                                return headers;
                            }
                        };

                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                10000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        RequestQueue requestQueue = Volley.newRequestQueue(DashboardActivity.this);
                        requestQueue.add(stringRequest);


                    }else{

                        String URL = "https://www.irms.in/api/menu_break";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("responseeeeeeeee", response);
                                currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    String Success = jsonObject.getString("status");
                                   String msg = jsonObject.getString("msg");

                                    if (Success.equals("200")) {
                                        travel.setBackgroundResource(R.drawable.peacockblue_circle);
                                        Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

                                        currentTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());
                                        break_time_txt.setVisibility(View.VISIBLE);
                                        break_time_txt.setText("Travel Time"+" ("+currentTime+")");

                                    } else {
                                       Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

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

                                params.put("time",new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));
                                params.put("type","travel");
                                params.put("mode", "enable");


                                Log.i("arrayyyyy", params.toString());
                                return params;

                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Accept", "application/json");
                                headers.put("Authorization", "Bearer " + PreferenceUtils.getToken(DashboardActivity.this));

                                return headers;
                            }
                        };

                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                10000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        RequestQueue requestQueue = Volley.newRequestQueue(DashboardActivity.this);
                        requestQueue.add(stringRequest);

                    }



                }
            });

        }
        else if(meeting.isPressed()){
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    break_time_txt.setVisibility(View.GONE);
                    if(d_meeting.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()) {
                        String URL = "https://www.irms.in/api/menu_break";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("responseeeeeeeee", response);
                                currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    String Success = jsonObject.getString("status");
                                    String msg = jsonObject.getString("msg");

                                    if (Success.equals("200")) {
                                        meeting.setBackgroundResource(R.drawable.blue_circle);
                                        Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

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

                                params.put("time",new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));
                                params.put("type","meeting");
                                params.put("mode", "disable");


                                Log.i("arrayyyyy", params.toString());
                                return params;

                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Accept", "application/json");
                                headers.put("Authorization", "Bearer " + PreferenceUtils.getToken(DashboardActivity.this));

                                return headers;
                            }
                        };

                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                10000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        RequestQueue requestQueue = Volley.newRequestQueue(DashboardActivity.this);
                        requestQueue.add(stringRequest);


                    }else{
                        String URL = "https://www.irms.in/api/menu_break";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("responseeeeeeeee", response);
                                currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    String Success = jsonObject.getString("status");
                                   String msg = jsonObject.getString("msg");

                                    if (Success.equals("200")) {
                                        meeting.setBackgroundResource(R.drawable.peacockblue_circle);
                                        Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

                                        currentTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());
                                        break_time_txt.setVisibility(View.VISIBLE);
                                        break_time_txt.setText("Meeting Time"+" ("+currentTime+")");

                                    } else {
                                        Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

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

                                params.put("time",new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));
                                params.put("type","meeting");
                                params.put("mode", "enable");


                                Log.i("arrayyyyy", params.toString());
                                return params;

                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Accept", "application/json");
                                headers.put("Authorization", "Bearer " + PreferenceUtils.getToken(DashboardActivity.this));

                                return headers;
                            }
                        };

                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                10000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        RequestQueue requestQueue = Volley.newRequestQueue(DashboardActivity.this);
                        requestQueue.add(stringRequest);

                    }


                }
            });

        }
        else if(break_time.isPressed()){
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    break_time_txt.setVisibility(View.GONE);
                    if(d_break.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()) {
                        String URL = "https://www.irms.in/api/menu_break";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("responseeeeeeeee", response);
                                currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    String Success = jsonObject.getString("status");
                                 String msg = jsonObject.getString("msg");

                                    if (Success.equals("200")) {
                                        break_time.setBackgroundResource(R.drawable.blue_circle);

                                        Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

                                    } else {
                                      Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

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

                                params.put("time",new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));
                                params.put("type","break");
                                params.put("mode", "disable");


                                Log.i("arrayyyyy", params.toString());
                                return params;

                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Accept", "application/json");
                                headers.put("Authorization", "Bearer " + PreferenceUtils.getToken(DashboardActivity.this));

                                return headers;
                            }
                        };

                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                10000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        RequestQueue requestQueue = Volley.newRequestQueue(DashboardActivity.this);
                        requestQueue.add(stringRequest);


                    }else{
                        String URL = "https://www.irms.in/api/menu_break";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("responseeeeeeeee", response);
                                currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    String Success = jsonObject.getString("status");
                                  String msg = jsonObject.getString("msg");

                                    if (Success.equals("200")) {
                                        break_time.setBackgroundResource(R.drawable.peacockblue_circle);
                                        Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

                                        currentTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());
                                        break_time_txt.setVisibility(View.VISIBLE);
                                        break_time_txt.setText("Break Time"+" ("+currentTime+")");

                                    } else {
                                     Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

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

                                params.put("time",new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));
                                params.put("type","break");
                                params.put("mode", "enable");


                                Log.i("arrayyyyy", params.toString());
                                return params;

                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Accept", "application/json");
                                headers.put("Authorization", "Bearer " + PreferenceUtils.getToken(DashboardActivity.this));

                                return headers;
                            }
                        };

                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                10000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        RequestQueue requestQueue = Volley.newRequestQueue(DashboardActivity.this);
                        requestQueue.add(stringRequest);

                    }




                }
            });

        }
        else if(leave.isPressed()){
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    break_time_txt.setVisibility(View.GONE);
                    if(d_leave.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()) {
                        String URL = "https://www.irms.in/api/menu_break";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("responseeeeeeeee", response);
                                currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    String Success = jsonObject.getString("status");
                                  String msg = jsonObject.getString("msg");

                                    if (Success.equals("200")) {
                                        leave.setBackgroundResource(R.drawable.blue_circle);
                                        Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

                                    } else {
                                       Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

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

                                params.put("time",new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));
                                params.put("type","leaves");
                                params.put("mode", "disable");


                                Log.i("arrayyyyy", params.toString());
                                return params;

                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Accept", "application/json");
                                headers.put("Authorization", "Bearer " + PreferenceUtils.getToken(DashboardActivity.this));

                                return headers;
                            }
                        };

                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                10000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        RequestQueue requestQueue = Volley.newRequestQueue(DashboardActivity.this);
                        requestQueue.add(stringRequest);


                    }else{
//                        Toast.makeText(DashboardActivity.this, "Already take leave for the day", Toast.LENGTH_SHORT).show();
                        String URL = "https://www.irms.in/api/menu_break";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("responseeeeeeeee", response);
                                currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    String Success = jsonObject.getString("status");
                                  String msg = jsonObject.getString("msg");

                                    if (Success.equals("200")) {
                                        leave.setBackgroundResource(R.drawable.peacockblue_circle);
                                        Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();
                                        currentTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());
                                        break_time_txt.setVisibility(View.VISIBLE);
                                        break_time_txt.setText("Leave Time"+" ("+currentTime+")");


                                    } else {

                                      Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

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

                                params.put("time",new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));
                                params.put("type","leaves");
                                params.put("mode", "enable");


                                Log.i("arrayyyyy", params.toString());
                                return params;

                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Accept", "application/json");
                                headers.put("Authorization", "Bearer " + PreferenceUtils.getToken(DashboardActivity.this));

                                return headers;
                            }
                        };

                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                10000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        RequestQueue requestQueue = Volley.newRequestQueue(DashboardActivity.this);
                        requestQueue.add(stringRequest);

                    }



                }
            });

        }
        else if(permission.isPressed()){
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    break_time_txt.setVisibility(View.GONE);
                    if(d_permission.getConstantState() == getResources().getDrawable(R.drawable.peacockblue_circle).getConstantState()) {

                        String URL = "https://www.irms.in/api/menu_break";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("responseeeeeeeee", response);
                                currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    String Success = jsonObject.getString("status");
                                    String msg = jsonObject.getString("msg");

                                    if (Success.equals("200")) {
                                        permission.setBackgroundResource(R.drawable.blue_circle);
                                        Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

                                    } else {
                                       Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

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

                                params.put("time",new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));
                                params.put("type","permission_time");
                                params.put("mode", "disable");


                                Log.i("arrayyyyy", params.toString());
                                return params;

                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Accept", "application/json");
                                headers.put("Authorization", "Bearer " + PreferenceUtils.getToken(DashboardActivity.this));

                                return headers;
                            }
                        };

                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                10000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        RequestQueue requestQueue = Volley.newRequestQueue(DashboardActivity.this);
                        requestQueue.add(stringRequest);


                    }else{
//                        Toast.makeText(DashboardActivity.this, "Already take permission for the day", Toast.LENGTH_SHORT).show();

                        String URL = "https://www.irms.in/api/menu_break";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("responseeeeeeeee", response);
                                currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    String Success = jsonObject.getString("status");
                                    String msg = jsonObject.getString("msg");

                                    if (Success.equals("200")) {
                                        permission.setBackgroundResource(R.drawable.peacockblue_circle);
                                        Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

                                        currentTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());
                                        break_time_txt.setVisibility(View.VISIBLE);
                                        break_time_txt.setText("Permission Time"+" ("+currentTime+")");

                                    } else {
                                       Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

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

                                params.put("time",new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));
                                params.put("type","permission_time");
                                params.put("mode", "enable");


                                Log.i("arrayyyyy", params.toString());
                                return params;

                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Accept", "application/json");
                                headers.put("Authorization", "Bearer " + PreferenceUtils.getToken(DashboardActivity.this));

                                return headers;
                            }
                        };

                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                10000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        RequestQueue requestQueue = Volley.newRequestQueue(DashboardActivity.this);
                        requestQueue.add(stringRequest);

                    }


                }
            });

        }

    }


    public void onBackPressed() {


        Dialog dialog;

        dialog = new Dialog(DashboardActivity.this);

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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PHOTO && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            Uri path = data.getData();

            profile_name.setVisibility(View.VISIBLE);
            profile_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uploadBitmap(bitmap);

                }
            });
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                avatar.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == CAMERA_REQUEST1 && resultCode == RESULT_OK) {

            profile_name.setVisibility(View.VISIBLE);
            profile_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    uploadBitmap(bitmap);

                }
            });
            bitmap = BitmapFactory.decodeFile(currentphoto);
            avatar.setImageBitmap(bitmap);




        }
    }
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadBitmap(Bitmap bitmap) {
        String URL = "https://www.irms.in/api/profile_img";
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.i("jfkhcduirfyd",response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data));
                            String Success = jsonObject.getString("status");
                            String msg = jsonObject.getString("msg");


                            if(Success.equals("200")){
                                Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

                            }



                        } catch (Exception e) {
                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer " + PreferenceUtils.getToken(DashboardActivity.this));
                return params;
            }


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();

                if (bitmap != null){
                    params.put("profile_photo", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));

                }

                return params;
            }
        };

        Volley.newRequestQueue(this).add(volleyMultipartRequest);

    }
    public void getBitmap() {
        String URL = "https://www.irms.in/api/view_profile";
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, URL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.i("jfkhcduirfyd",response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data));
                            String Success = jsonObject.getString("status");
                            String msg = jsonObject.getString("msg");
                            String image_url = jsonObject.getString("image_url");


                            if(Success.equals("200")){
                                Glide.with(DashboardActivity.this)
                                        .load(image_url)
                                        .into(avatar);

                            }


                        } catch (Exception e) {
                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer " + PreferenceUtils.getToken(DashboardActivity.this));
                return params;
            }


        };

        Volley.newRequestQueue(this).add(volleyMultipartRequest);

    }

    class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(@NonNull Location location) {

            if (location != null) {

                locationManager.removeUpdates(locationListener);

                lat = "" + location.getLatitude();
                longi = "" + location.getLongitude();

                geocoder = new Geocoder(DashboardActivity.this,Locale.getDefault());
                try {
                    myaddress = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String address = myaddress.get(0).getAddressLine(0);


                location_txt.setText(address);
                headline.setText("Your Current Location");

            }
        }

        @Override
        public void onLocationChanged(@NonNull List<Location> locations) {
            LocationListener.super.onLocationChanged(locations);
        }

        @Override
        public void onFlushComplete(int requestCode) {
            LocationListener.super.onFlushComplete(requestCode);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            LocationListener.super.onStatusChanged(provider, status, extras);
        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {
            LocationListener.super.onProviderEnabled(provider);
        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {
            LocationListener.super.onProviderDisabled(provider);
        }
    }



    private boolean checklocationpermission(){

        int location = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);
        int location2 = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION);

        List<String> listpermission = new ArrayList<>();

        if(location!=PackageManager.PERMISSION_GRANTED){
            listpermission.add(Manifest.permission.ACCESS_FINE_LOCATION);

        }
        if(location2!=PackageManager.PERMISSION_GRANTED){
            listpermission.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        }
        if(!listpermission.isEmpty()){

            ActivityCompat.requestPermissions(this,listpermission.toArray(new String[listpermission.size()]),
                    1);

        }

        return  true;

    }


    private void buildAlertMessageNoGps() {

        Dialog dialog;

        dialog = new Dialog(DashboardActivity.this);


        dialog.setContentView(R.layout.alert_dailogbox);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();


        TextView title = dialog.findViewById(R.id.title);
        RelativeLayout no = dialog.findViewById(R.id.no);
        RelativeLayout yes = dialog.findViewById(R.id.yes);

        title.setText("Your GPS seems to be disabled, do you want to enable it?");


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

                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));


            }
        });




    }

    public void check_btn(){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);

    }
    public void check_in(){
        String URL = "https://www.irms.in/api/check_in_out";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("responseeeeeeeee", response);

                currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String Success = jsonObject.getString("status");
                    String msg = jsonObject.getString("msg");

                    if (Success.equals("200")) {

                        Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

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

                params.put("time", new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date())+" am");
                params.put("type", "check_in");
                params.put("mode_type",home_txt.getText().toString());
                params.put("address",location_txt.getText().toString());

                Log.i("arrayyyyy", params.toString());
                return params;

            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer "+ PreferenceUtils.getToken(DashboardActivity.this));

                return headers;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(DashboardActivity.this);
        requestQueue.add(stringRequest);

    }

    public void check_out(){
        String URL = "https://www.irms.in/api/check_in_out";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("responseeeeeeeee", response);

                currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String Success = jsonObject.getString("status");
                    String msg = jsonObject.getString("msg");

                    if (Success.equals("200")) {

                        Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

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

                params.put("time", new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date())+" am");
                params.put("type", "check_out");
                params.put("mode_type",home_txt.getText().toString());
                params.put("address",location_txt.getText().toString());

                Log.i("arrayyyyy", params.toString());
                return params;

            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer "+ PreferenceUtils.getToken(DashboardActivity.this));

                return headers;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(DashboardActivity.this);
        requestQueue.add(stringRequest);

    }



//    private void makePhoneCall() {
//       phone = "9626231757";
//       String number = phone;
//
//        if (number.trim().length() > 0) {
//
//            if (ContextCompat.checkSelfPermission(DashboardActivity.this,
//                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(DashboardActivity.this,
//                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
//            } else {
//                String dial = "tel:" + number;
//                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
//            }
//
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_CALL) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                makePhoneCall();
//            } else {
//                Toast.makeText(DashboardActivity.this, "Permission Denied to make a call" + "", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//


}

