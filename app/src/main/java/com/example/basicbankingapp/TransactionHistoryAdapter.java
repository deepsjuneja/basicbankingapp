package com.example.basicbankingapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<ViewHolder> {
    TransactionHistoryActivity trans_list_activity;
    List<Transaction> transList;
    RelativeLayout historyLayout;

    public TransactionHistoryAdapter(TransactionHistoryActivity transactionListActivity, List<Transaction> transactionList) {
        this.trans_list_activity = transactionListActivity;
        this.transList = transactionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.transhistory_layout, parent, false);

        historyLayout = itemView.findViewById(R.id.transHistoryView);

        ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.sender_name.setText(transList.get(position).getSenderName());
        holder.receiver_name.setText(transList.get(position).getReceiverName());
        holder.transaction_amount.setText("Rs. " + (transList.get(position).getTransAmount()));
        holder.trans_date.setText(transList.get(position).getTransDate());

        if(transList.get(position).getTransStatus().equals("Failed"))
            holder.historyLayout.setBackgroundColor(Color.parseColor("#FF6C5C"));
    }

    @Override
    public int getItemCount() {
        return transList.size();
    }
}