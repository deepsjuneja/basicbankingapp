package com.example.basicbankingapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomerListActivity extends AppCompatActivity {
//    private static RecyclerView.Adapter adapter;
    private static RecyclerView recyclerView;
    CustomerAdapter customerAdapter;
    BankDBHelper db;
    List<Customer> customerList = new ArrayList<>();
    String customerId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_customerlist);
        recyclerView = findViewById(R.id.recycler_view);

        db = new BankDBHelper(this);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));

        fetchCustomerData();
    }

    private void fetchCustomerData() {
        customerList.clear();
        Cursor cursor = new BankDBHelper(this).readCustomersList(this);
        while(cursor.moveToNext()){
            Customer customer = new Customer(cursor.getString(0), cursor.getString(1), cursor.getString(4));
            customerList.add(customer);
        }

        customerAdapter = new CustomerAdapter(CustomerListActivity.this, customerList);
        recyclerView.setAdapter(customerAdapter);
    }

    public void detailsActivity(int position) {
//        showData();
        customerId = customerList.get(position).getId();
        Intent intent = new Intent(CustomerListActivity.this, CustomerDetailsActivity.class);
        intent.putExtra("customerId",customerId);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(CustomerListActivity.this, TransactionHistoryActivity.class));
        return super.onOptionsItemSelected(item);
    }
}
