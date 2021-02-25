package com.example.basicbankingapp;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<Integer> mCustomer_id;
    private List<String> mCustomer_name;
    private List<Double> mCustomer_balance;
    Activity mActivity;
    CustomerListActivity customer_list_activity;
    List<Customer> custList;

    public CustomerAdapter(Activity activity, List<Integer> customer_id, List<String> customer_name, List<Double> customer_balance) {
        this.mActivity = activity;
        this.mCustomer_id = customer_id;
        this.mCustomer_name = customer_name;
        this.mCustomer_balance = customer_balance;
    }

    public CustomerAdapter(CustomerListActivity customerListActivity, List<Customer> customerList) {
        this.customer_list_activity = customerListActivity;
        this.custList = customerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                customer_list_activity.detailsActivity(position);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.customer_name.setText(custList.get(position).getName());
        holder.customer_balance.setText("Rs. " + (custList.get(position).getBalance()));
    }

    @Override
    public int getItemCount() {
//        return mCustomer_id.size();
        return custList.size();
    }
}