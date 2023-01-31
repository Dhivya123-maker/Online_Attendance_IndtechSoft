package EmployeeAttendance;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.online_attendance_indtechsoft.R;

import java.util.List;

public class EmployeeAttendanceAdapter extends RecyclerView.Adapter<EmployeeAttendanceAdapter.ViewHolder> {

    List<EmployeeAttendanceModel> employeeAttendanceModelList;
    private Context context;


    public EmployeeAttendanceAdapter(Context context, List<EmployeeAttendanceModel> employeeAttendanceModelList) {
        this.context = context;
        this.employeeAttendanceModelList = employeeAttendanceModelList;

    }

    @NonNull
    @Override
    public EmployeeAttendanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_attendance_recycle,parent,false);

        return new EmployeeAttendanceAdapter.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.s_no.setText(employeeAttendanceModelList.get(position).getS_no());
        holder.name.setText(employeeAttendanceModelList.get(position).getName());
        holder.user_role.setText(employeeAttendanceModelList.get(position).getUser_role());
        holder.login.setText(employeeAttendanceModelList.get(position).getLogin_time());
        holder.logout.setText(employeeAttendanceModelList.get(position).getLogout_time());
        holder.lunch_s.setText(employeeAttendanceModelList.get(position).getLunch_start());
        holder.lunch_e.setText(employeeAttendanceModelList.get(position).getLunch_end());
        holder.tea_s.setText(employeeAttendanceModelList.get(position).getTea_start());
        holder.tea_e.setText(employeeAttendanceModelList.get(position).getTea_end());
        holder.travel_s.setText(employeeAttendanceModelList.get(position).getTravel_start());
        holder.travel_e.setText(employeeAttendanceModelList.get(position).getTravel_end());
        holder.meeting_s.setText(employeeAttendanceModelList.get(position).getMeeting_start());
        holder.meeting_e.setText(employeeAttendanceModelList.get(position).getMeeting_end());
        holder.break_s.setText(employeeAttendanceModelList.get(position).getBreak_start());
        holder.break_e.setText(employeeAttendanceModelList.get(position).getBreak_end());
        holder.permission_s.setText(employeeAttendanceModelList.get(position).getPermission_start());
        holder.permission_e.setText(employeeAttendanceModelList.get(position).getPermission_end());



        if(position ==0){
            holder.one.setVisibility(View.VISIBLE);
            holder.two.setVisibility(View.VISIBLE);
            holder.three.setVisibility(View.VISIBLE);
            holder.four.setVisibility(View.VISIBLE);
            holder.five.setVisibility(View.VISIBLE);
            holder.six.setVisibility(View.VISIBLE);
            holder.seven.setVisibility(View.VISIBLE);
            holder.eight.setVisibility(View.VISIBLE);
            holder.nine.setVisibility(View.VISIBLE);
            holder.ten.setVisibility(View.VISIBLE);
            holder.eleven.setVisibility(View.VISIBLE);
            holder.twelve.setVisibility(View.VISIBLE);
            holder.thirteen.setVisibility(View.VISIBLE);
            holder.fourteen.setVisibility(View.VISIBLE);
            holder.fifteen.setVisibility(View.VISIBLE);
            holder.sixteen.setVisibility(View.VISIBLE);
            holder.seventeen.setVisibility(View.VISIBLE);

        }

        else{
            holder.one.setVisibility(View.GONE);
            holder.two.setVisibility(View.GONE);
            holder.three.setVisibility(View.GONE);
            holder.four.setVisibility(View.GONE);
            holder.five.setVisibility(View.GONE);
            holder.six.setVisibility(View.GONE);
            holder.seven.setVisibility(View.GONE);
            holder.eight.setVisibility(View.GONE);
            holder.nine.setVisibility(View.GONE);
            holder.ten.setVisibility(View.GONE);
            holder.eleven.setVisibility(View.GONE);
            holder.twelve.setVisibility(View.GONE);
            holder.thirteen.setVisibility(View.GONE);
            holder.fourteen.setVisibility(View.GONE);
            holder.fifteen.setVisibility(View.GONE);
            holder.sixteen.setVisibility(View.GONE);
            holder.seventeen.setVisibility(View.GONE);
        }


    }


    // total number of rows
    @Override
    public int getItemCount() {


        return employeeAttendanceModelList.size();

    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView s_no,name,user_role,login,logout,lunch_s,lunch_e,tea_s,tea_e,travel_s,travel_e,meeting_s,meeting_e,
        break_s,break_e,permission_s,permission_e;
        TextView one, two, three, four, five, six,seven,eight,nine,ten,eleven,twelve,thirteen,fourteen,fifteen,sixteen,seventeen;


        ViewHolder(View itemView) {
            super(itemView);

            s_no = itemView.findViewById(R.id.s_no);
            name = itemView.findViewById(R.id.name);
            user_role = itemView.findViewById(R.id.user_role);
            login = itemView.findViewById(R.id.login);
            logout = itemView.findViewById(R.id.logout);
            lunch_s = itemView.findViewById(R.id.lunch_s);
            lunch_e = itemView.findViewById(R.id.lunch_e);
            tea_s = itemView.findViewById(R.id.tea_s);
            tea_e = itemView.findViewById(R.id.tea_e);
            travel_s = itemView.findViewById(R.id.travel_s);
            travel_e = itemView.findViewById(R.id.travel_e);
            meeting_s = itemView.findViewById(R.id.meeting_s);
            meeting_e = itemView.findViewById(R.id.meeting_e);
            break_s = itemView.findViewById(R.id.break_s);
            break_e = itemView.findViewById(R.id.break_e);
            permission_s = itemView.findViewById(R.id.permission_s);
            permission_e = itemView.findViewById(R.id.permission_e);


            one = itemView.findViewById(R.id.one);
            two = itemView.findViewById(R.id.two);
            three = itemView.findViewById(R.id.three);
            four = itemView.findViewById(R.id.four);
            five = itemView.findViewById(R.id.five);
            six = itemView.findViewById(R.id.six);
            seven= itemView.findViewById(R.id.seven);
            eight = itemView.findViewById(R.id.eight);
            nine = itemView.findViewById(R.id.nine);
            ten = itemView.findViewById(R.id.ten);
            eleven = itemView.findViewById(R.id.eleven);
            twelve = itemView.findViewById(R.id.twelve);
            thirteen = itemView.findViewById(R.id.thirteen);
            fourteen = itemView.findViewById(R.id.fourteen);
            fifteen = itemView.findViewById(R.id.fifteen);
            sixteen = itemView.findViewById(R.id.sixteen);
            seventeen = itemView.findViewById(R.id.seventeen);
        }


    }




}