package com.charan.anthenasquare.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.charan.anthenasquare.Entites.Intern;
import com.charan.anthenasquare.R;

import java.util.List;

public class InternAdapter extends RecyclerView.Adapter<InternAdapter.viewHolder>{

    List<Intern> internList;



    public InternAdapter(List<Intern> list){
        this.internList = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.internship_card, parent, false);
        return new InternAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        Intern intern = internList.get(position);

        holder.role.setText(intern.getRole());
        holder.company.setText(intern.getCompanyName());
        String dateValue = intern.getStartDate() + " to "+ intern.getEndDate();
        holder.date.setText(dateValue);
        holder.desp.setText(intern.getDescription());


    }

    @Override
    public int getItemCount() {
        return internList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView company,role,date,desp;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            company = itemView.findViewById(R.id.companyName);
            role = itemView.findViewById(R.id.role);
            date = itemView.findViewById(R.id.date);
            desp = itemView.findViewById(R.id.discription);
        }
    }
}
