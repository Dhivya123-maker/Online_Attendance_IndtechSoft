package com.example.online_attendance_indtechsoft.Task;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewTaskAdapter extends RecyclerView.Adapter<ViewTaskAdapter.ViewHolder> {

    List<ViewTaskModel> taskModelList;
    Context context;
    RadioButton status_radio_button;
    RadioGroup radioGroup;

    public ViewTaskAdapter(Context context, List<ViewTaskModel> taskModelList) {

        this.taskModelList = taskModelList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_recycle, parent, false);


        return new ViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.task_id.setText(taskModelList.get(position).getTask_id());
        holder.task_name.setText(taskModelList.get(position).getTask_name());
        holder.task_assigned_by.setText(taskModelList.get(position).getTask_assigned_by());
        holder.task_assigned_to.setText(taskModelList.get(position).getTask_assigned_to());


    }

    @Override
    public int getItemCount() {
                return taskModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView task_id,task_name,task_assigned_to,task_assigned_by,status,update_status;
        ImageView update;
        LinearLayout status_lnr;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            task_id = itemView.findViewById(R.id.task_id);
            task_name = itemView.findViewById(R.id.task_name);
            task_assigned_to = itemView.findViewById(R.id.task_assigned_to);
            task_assigned_by = itemView.findViewById(R.id.task_assigned_by);
            status = itemView.findViewById(R.id.status_txt);
            update_status = itemView.findViewById(R.id.update_status);
            status_lnr = itemView.findViewById(R.id.status_lnr);

            update = itemView.findViewById(R.id.update_img);
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    String id = taskModelList.get(position).getTask_id();
                    String name = taskModelList.get(position).getTask_name();
                    String assign_by = taskModelList.get(position).getTask_assigned_by();
                    String assign_to = taskModelList.get(position).getTask_assigned_to();

                    Intent intent = new Intent(view.getContext(),UpdateTaskActivity.class);
                    intent.putExtra("id",id);
                    intent.putExtra("name",name);
                    intent.putExtra("assign_by",assign_by);
                    intent.putExtra("assign_to",assign_to);
                    view.getContext().startActivity(intent);

                }
            });


            update_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Dialog dialog;

                    dialog = new Dialog(context);

                    // set custom dialog
                    dialog.setContentView(R.layout.status_recycle);

                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                    // set transparent background
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    // show dialog
                    dialog.show();


                    radioGroup = dialog.findViewById(R.id.radioGroup);
                    RelativeLayout ok,cancel;
                    ok = dialog.findViewById(R.id.yes);
                    cancel = dialog.findViewById(R.id.no);



                    ok.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onClick(View view) {
                            int position = getAdapterPosition();
                            String id = taskModelList.get(position).getTask_id();

                            int selectedId = radioGroup.getCheckedRadioButtonId();
                            status_radio_button = dialog.findViewById(selectedId);
                            if(selectedId==-1){
                                Toast.makeText(context,"Select one choice", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                dialog.dismiss();
                                String URL = "https://www.irms.in/api/task_status";
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

                                        params.put("task_id",id);
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
                                status_lnr.setVisibility(View.VISIBLE);
                                status.setText(status_radio_button.getText().toString());
                                if(status_radio_button.getText().toString().equals("Completed")){
                                    status.setTextColor(context.getResources().getColor(R.color.green));
                                }else{
                                    status.setTextColor(context.getResources().getColor(R.color.red));
                                }

                            }


                        }
                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();

                        }
                    });

                }
            });


        }


    }


}