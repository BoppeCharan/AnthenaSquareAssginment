package com.charan.anthenasquare.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.charan.anthenasquare.R;

import java.util.List;

public class SingleValueAdapter extends RecyclerView.Adapter<SingleValueAdapter.viewHolder> {

    List<String> itemsList;



    public SingleValueAdapter(List<String> list){
        this.itemsList = list;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_value_card, parent, false);
        return new SingleValueAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

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
