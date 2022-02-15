package com.charan.anthenasquare.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.charan.anthenasquare.R;

import java.util.List;

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.viewHolder> {

    List<String> itemsList;



    public SkillAdapter(List<String> list){
        this.itemsList = list;
    }
    @NonNull
    @Override
    public SkillAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_value_card, parent, false);
        return new SkillAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillAdapter.viewHolder holder, int position) {

        holder.valueRes.setText(itemsList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.itemsList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView valueRes;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            valueRes = itemView.findViewById(R.id.value);
        }
    }
}
