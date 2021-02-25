package com.example.basicbankingapp;

import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView customer_name, customer_balance, sender_name, receiver_name, transaction_amount, trans_date;
    RelativeLayout historyLayout;
    View mView;
    private AdapterView.OnItemClickListener clickListener;

    public ViewHolder(View itemView) {
        super(itemView);
        this.customer_name = itemView.findViewById(R.id.nameTextView);
        this.customer_balance = itemView.findViewById(R.id.balanceTextView);
        this.sender_name = itemView.findViewById(R.id.senderTextView);
        this.receiver_name = itemView.findViewById(R.id.receiverTextView);
        this.transaction_amount = itemView.findViewById(R.id.transAmountTextView);
        this.trans_date = itemView.findViewById(R.id.dateTextView);
        this.historyLayout = itemView.findViewById(R.id.transHistoryView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });

    }

    private ViewHolder.ClickListener mClickListener;

    public interface ClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnClickListener(ViewHolder.ClickListener clickListener) {
        mClickListener = clickListener;
    }
}