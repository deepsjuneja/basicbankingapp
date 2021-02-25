package com.example.basicbankingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ReceiverAdapter extends RecyclerView.Adapter<ViewHolder> {
    ReceiverListActivity receiver_list_activity;
    List<Customer> receiveList;

    public ReceiverAdapter(ReceiverListActivity receiverListActivity, List<Customer> receiverList) {
        this.receiver_list_activity = receiverListActivity;
        this.receiveList = receiverList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                receiver_list_activity.selectReceiver(position);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.customer_name.setText(receiveList.get(position).getName());
        holder.customer_balance.setText("Rs. " + (receiveList.get(position).getBalance()));
    }

    @Override
    public int getItemCount() {
        return receiveList.size();
    }

}