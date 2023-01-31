package com.example.online_attendance_indtechsoft;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.online_attendance_indtechsoft.Meeting.MeetingActivity;
import com.example.online_attendance_indtechsoft.utils.PreferenceUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageCaptureActivity extends AppCompatActivity {

    ImageView image_upload,image_btn,back;
    int CAMERA_REQUEST1 = 1;
    int SELECT_PHOTO = 2;
    String currentphoto;
    Bitmap bitmap;
    Button post;
    EditText remarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_capture);

        image_upload = findViewById(R.id.post_image);
        image_btn = findViewById(R.id.post_btn);
        back = findViewById(R.id.back);
        post = findViewById(R.id.post);
        remarks = findViewById(R.id.description);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Remarks = remarks.getText().toString();

                if(image_btn.getVisibility()==View.VISIBLE){
                    Toast.makeText(ImageCaptureActivity.this, "Please upload site image", Toast.LENGTH_SHORT).show();

                }else
                if(Remarks.isEmpty()){
                    Toast.makeText(ImageCaptureActivity.this, "remarks field is required", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ImageCaptureActivity.this, "Site image uploaded successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ImageCaptureActivity.this,DashboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    uploadBitmap(bitmap);

                }


            }
        });

        image_upload.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                BottomSheetDialog dialog = new BottomSheetDialog(ImageCaptureActivity.this);

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
                            Uri imageuri = FileProvider.getUriForFile(ImageCaptureActivity.this,"com.example.online_attendance_indtechsoft.provider",imagefile);

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

    }

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == SELECT_PHOTO && resultCode == AppCompatActivity.RESULT_OK && data != null) {
        Uri path = data.getData();

        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
            image_upload.setImageBitmap(bitmap);
            image_btn.setVisibility(View.GONE);

            uploadBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    if (requestCode == CAMERA_REQUEST1 && resultCode == RESULT_OK) {

            bitmap = BitmapFactory.decodeFile(currentphoto);

            int w = bitmap.getWidth();
            int h = bitmap.getHeight();

            Matrix mtx = new Matrix();
            mtx.postRotate(90);

            Bitmap rotatedBMP = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
            BitmapDrawable bmd = new BitmapDrawable(rotatedBMP);
            image_upload.setImageDrawable(bmd);
            image_btn.setVisibility(View.GONE);

            uploadBitmap(bitmap);


    }

}
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadBitmap(Bitmap bitmap) {
        String URL = "https://irms.in/api/site_image";
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.i("jfkhcduirfyd",response.toString());

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
                params.put("Authorization", "Bearer " +PreferenceUtils.getToken(ImageCaptureActivity.this));
                return params;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("description",remarks.getText().toString());

                return params;

            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();

                if (bitmap != null){
                    params.put("site_photo", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));

                }


                return params;
            }
        };

        Volley.newRequestQueue(this).add(volleyMultipartRequest);

    }

}