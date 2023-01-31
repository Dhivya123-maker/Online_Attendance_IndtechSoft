package LeadCallHistory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.online_attendance_indtechsoft.R;

import java.util.List;

class LeadCallHistoryAdpter extends RecyclerView.Adapter<LeadCallHistoryAdpter.ViewHolder> {

    List<LeadCallHistoryModel> leadHistoryModelList;
    private Context context;

    public LeadCallHistoryAdpter(Context context, List<LeadCallHistoryModel> leadHistoryModelList) {
        this.context = context;
        this.leadHistoryModelList = leadHistoryModelList;

    }


    @NonNull
    @Override
    public LeadCallHistoryAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.call_logs_recycle,parent,false);

        return new LeadCallHistoryAdpter.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull LeadCallHistoryAdpter.ViewHolder holder, int position) {
        holder.call_name.setText(leadHistoryModelList.get(position).getName());
        holder.call_num.setText(leadHistoryModelList.get(position).getNumber());
        holder.call_start.setText(leadHistoryModelList.get(position).getStart_time());
        holder.call_end.setText(leadHistoryModelList.get(position).getEnd_time());
        holder.call_duration.setText(leadHistoryModelList.get(position).getDuration());

    }


    // total number of rows
    @Override
    public int getItemCount() {
        return leadHistoryModelList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView call_name,call_num,call_start,call_end,call_duration;


        ViewHolder(View itemView) {
            super(itemView);


            call_name = itemView.findViewById(R.id.call_name);
            call_num = itemView.findViewById(R.id.call_number);
            call_start = itemView.findViewById(R.id.start_time);
            call_end = itemView.findViewById(R.id.end_time);
            call_duration = itemView.findViewById(R.id.call_duration);



        }

    }


}