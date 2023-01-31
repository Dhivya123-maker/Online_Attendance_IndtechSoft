package com.example.online_attendance_indtechsoft.Notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.online_attendance_indtechsoft.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    List<NotificationModel> notificationModelList;
    Context context;


    public NotificationAdapter(Context context, List<NotificationModel> notificationModelList) {
        this.notificationModelList = notificationModelList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_recycle, parent, false);
        return new ViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.notification_title.setText(notificationModelList.get(position).getTitle());
        holder.notification_text.setText(notificationModelList.get(position).getMsg());

    }

    @Override
    public int getItemCount() {
        return notificationModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView notification_title,notification_text;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            notification_title = itemView.findViewById(R.id.notification_title);
            notification_text = itemView.findViewById(R.id.notification_text);

        }

    }
}