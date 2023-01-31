package EmployeeInfo;

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

public class EmployeeInfoAdapter extends RecyclerView.Adapter<EmployeeInfoAdapter.ViewHolder> {

    List<EmployeeInfoModel> employeeInfoModelList;
    private Context context;


    public EmployeeInfoAdapter(Context context, List<EmployeeInfoModel> employeeInfoModelList) {
        this.context = context;
        this.employeeInfoModelList = employeeInfoModelList;

    }

    @NonNull
    @Override
    public EmployeeInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_info_recycle,parent,false);


        return new EmployeeInfoAdapter.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.s_no.setText(employeeInfoModelList.get(position).getS_no());
        holder.emp_code.setText(employeeInfoModelList.get(position).getEmp_code());
        holder.emp_name.setText(employeeInfoModelList.get(position).getEmp_name());
        holder.email.setText(employeeInfoModelList.get(position).getEmail());
        holder.phone_no.setText(employeeInfoModelList.get(position).getMobile_no());



        if(position ==0){
            holder.one.setVisibility(View.VISIBLE);
            holder.two.setVisibility(View.VISIBLE);
            holder.three.setVisibility(View.VISIBLE);
            holder.four.setVisibility(View.VISIBLE);
            holder.five.setVisibility(View.VISIBLE);

        }

        else{
            holder.one.setVisibility(View.GONE);
            holder.two.setVisibility(View.GONE);
            holder.three.setVisibility(View.GONE);
            holder.four.setVisibility(View.GONE);
            holder.five.setVisibility(View.GONE);

        }


    }


    // total number of rows
    @Override
    public int getItemCount() {


        return employeeInfoModelList.size();

    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView s_no,emp_code,emp_name,email,phone_no;
        TextView one, two, three, four, five;


        ViewHolder(View itemView) {
            super(itemView);

            s_no = itemView.findViewById(R.id.s_no);
            emp_code = itemView.findViewById(R.id.emp_code);
            emp_name = itemView.findViewById(R.id.emp_name);
            email = itemView.findViewById(R.id.email);
            phone_no = itemView.findViewById(R.id.phone_no);


            one = itemView.findViewById(R.id.one);
            two = itemView.findViewById(R.id.two);
            three = itemView.findViewById(R.id.three);
            four = itemView.findViewById(R.id.four);
            five = itemView.findViewById(R.id.five);



        }


    }




}