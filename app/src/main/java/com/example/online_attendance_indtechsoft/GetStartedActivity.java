package com.example.online_attendance_indtechsoft;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.online_attendance_indtechsoft.utils.PreferenceUtils;


public class GetStartedActivity extends AppCompatActivity {

    Button getstarted_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);


        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);

        getstarted_btn = findViewById(R.id.getstarted_btn);

        getstarted_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(GetStartedActivity.this,LoginActivity.class));

//                if (PreferenceUtils.getToken(GetStartedActivity.this) == null) {
//                  startActivity(new Intent(GetStartedActivity.this,LoginActivity.class));
//                }else{
//                    startActivity(new Intent(GetStartedActivity.this,DashboardActivity.class));
//                }
            }
        });
    }

    public void onBackPressed() {


        Dialog dialog;

        dialog=new Dialog(GetStartedActivity.this);

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

}