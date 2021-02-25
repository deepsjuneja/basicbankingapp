package com.example.basicbankingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReceiverListActivity extends AppCompatActivity {
//    private static RecyclerView.Adapter adapter;
    private static RecyclerView recyclerView;
    ReceiverAdapter receiverAdapter;
    BankDBHelper db;
    List<Customer> receiverList = new ArrayList<>();
    String senderId, senderName, receiverId, receiverName, transDate;
    double senderBalance, receiverBalance, transferredAmount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiverlist);

        recyclerView = findViewById(R.id.recycler_view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        db = new BankDBHelper(this);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yy, HH:mm");
        transDate = simpleDateFormat.format(calendar.getTime());

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            senderId = bundle.getString("senderId");
            senderName = bundle.getString("senderName");
            senderBalance = bundle.getDouble("currBalance");
            transferredAmount = bundle.getDouble("transferredAmount");
        }

        fetchReceivers();
    }

    private void fetchReceivers() {
        receiverList.clear();
        Cursor cursor = new BankDBHelper(this).readReceivers(this, Integer.parseInt(senderId));
        while(cursor.moveToNext()){
            Customer customer = new Customer(
                    cursor.getString(0), cursor.getString(1), cursor.getString(4));
            receiverList.add(customer);
        }

        receiverAdapter = new ReceiverAdapter(ReceiverListActivity.this, receiverList);
        recyclerView.setAdapter(receiverAdapter);
    }

    public void selectReceiver(int position) {
        receiverId = receiverList.get(position).getId();
        Cursor cursor = new BankDBHelper(this).readCustomerData(this, Integer.parseInt(receiverId));
        while(cursor.moveToNext()) {
            receiverName = cursor.getString(1);
            receiverBalance = cursor.getDouble(4);
            double updatedBalance_receiver = receiverBalance + transferredAmount;
            double updatedBalance_sender = senderBalance - transferredAmount;

            new BankDBHelper(this).addTransaction(
                    this, transDate, senderName, receiverName, transferredAmount, "Success");
            new BankDBHelper(this).updateBalance(this, receiverId, updatedBalance_receiver);
            new BankDBHelper(this).updateBalance(this, senderId, updatedBalance_sender);

            Toast.makeText(this, "Transaction is being processed....", Toast.LENGTH_LONG).show();
            startActivity(new Intent(ReceiverListActivity.this, TransferActivity.class));
//            finish();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder_backButton = new AlertDialog.Builder(ReceiverListActivity.this);
        builder_backButton.setTitle("Cancel the transaction?").setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new BankDBHelper(ReceiverListActivity.this).addTransaction(
                                ReceiverListActivity.this, transDate, senderName, "Not selected", transferredAmount, "Failed");
                        Toast.makeText(ReceiverListActivity.this, "Transaction Cancelled!!!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ReceiverListActivity.this, CustomerListActivity.class));
                        finish();
                    }
                }).setNegativeButton("NO", null);
        AlertDialog cancelAlert = builder_backButton.create();
        cancelAlert.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        AlertDialog.Builder builder_backButton = new AlertDialog.Builder(ReceiverListActivity.this);
        builder_backButton.setTitle("Cancel the transaction?").setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new BankDBHelper(ReceiverListActivity.this).addTransaction(
                                ReceiverListActivity.this, transDate, senderName, "----", transferredAmount, "Failed");
                        Toast.makeText(ReceiverListActivity.this, "Transaction Cancelled!!!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ReceiverListActivity.this, CustomerListActivity.class));
                        finish();
                    }
                }).setNegativeButton("NO", null);
        AlertDialog cancelAlert = builder_backButton.create();
        cancelAlert.show();
        return super.onOptionsItemSelected(item);
    }
}