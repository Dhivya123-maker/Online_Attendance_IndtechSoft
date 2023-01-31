package com.example.online_attendance_indtechsoft.Leads;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.online_attendance_indtechsoft.LeadAllocation.LeadAllocationActivity;
import com.example.online_attendance_indtechsoft.R;

import LeadCallHistory.LeadCallHistoryActivity;

public class LeadActivity extends AppCompatActivity {

    LinearLayout lead_info,lead_allocate,call_log_lnr;
    ImageView back;
    EditText remarks;
    Button submit;
    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);

        lead_info = findViewById(R.id.lead_info_lnr);
        lead_allocate = findViewById(R.id.lead_allocate_lnr);
        back = findViewById(R.id.back);
        remarks = findViewById(R.id.remarks);
        submit = findViewById(R.id.submit_button);
        textview = findViewById(R.id.textview);
        call_log_lnr = findViewById(R.id.call_log_lnr);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remarks.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
            }
        });
        call_log_lnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LeadActivity.this, LeadCallHistoryActivity.class));

            }
        });


        lead_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LeadActivity.this,LeadInformationActivity.class));
            }
        });

        lead_allocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LeadActivity.this, LeadAllocationActivity.class));
            }
        });
    }
//

}