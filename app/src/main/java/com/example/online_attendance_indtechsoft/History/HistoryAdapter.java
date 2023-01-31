package com.example.online_attendance_indtechsoft.History;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.online_attendance_indtechsoft.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    List<HistoryModel> historyModelList;
    Context context;





    public HistoryAdapter(Context context, List<HistoryModel> historyModelList) {

        this.historyModelList = historyModelList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_recycle, parent, false);


        return new ViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.location.setText(historyModelList.get(position).getLocation());
        holder.time.setText(historyModelList.get(position).getTime());

    }

    @Override
    public int getItemCount() {
                return historyModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView location,time;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            location = itemView.findViewById(R.id.location_text);
            time = itemView.findViewById(R.id.time_txt);



        }


    }
}