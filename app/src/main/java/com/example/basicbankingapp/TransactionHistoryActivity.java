package com.example.basicbankingapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryActivity extends AppCompatActivity {
//    private static RecyclerView.Adapter adapter;
    private static RecyclerView recyclerView;
    TransactionHistoryAdapter transactionHistoryAdapter;
    BankDBHelper db;
    List<Transaction> transactionList = new ArrayList<>();
    TextView no_history_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_transhistorylist);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        no_history_view = findViewById(R.id.no_history);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        db = new BankDBHelper(this);

        fetchTransactions();
    }

    private void fetchTransactions() {
        transactionList.clear();
        Cursor cursor = new BankDBHelper(this).readTransactions(this);
        while(cursor.moveToNext()){
            Transaction transaction = new Transaction(cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5));
            transactionList.add(transaction);
        }

        transactionHistoryAdapter = new TransactionHistoryAdapter(TransactionHistoryActivity.this, transactionList);
        recyclerView.setAdapter(transactionHistoryAdapter);

        if (transactionList.size() == 0)
            no_history_view.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            startActivity(new Intent(TransactionHistoryActivity.this, CustomerListActivity.class));

        if(item.getItemId() == R.id.homeIcon)
            startActivity(new Intent(TransactionHistoryActivity.this, HomeActivity.class));

        return super.onOptionsItemSelected(item);
    }
}
