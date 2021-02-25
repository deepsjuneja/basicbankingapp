package com.example.basicbankingapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class CustomerDetailsActivity extends AppCompatActivity {
    TextView nameView,emailView,accountnumView,balanceView;
    Button transferButton;
    double currBalance = 0.0;
    String senderId, senderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_details_layout);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        int customerID;
        String custID = "";
        if (bundle != null) {
            custID = bundle.getString("customerId");
        }

        customerID = Integer.parseInt(custID);
        senderId = custID;

        nameView = findViewById(R.id.nameTextView);
        emailView = findViewById(R.id.emailTextView);
        accountnumView = findViewById(R.id.accountTextView);
        balanceView = findViewById(R.id.balanceTextView);
        transferButton = findViewById(R.id.transactionButton);

        Cursor cursor = new BankDBHelper(this).readCustomerData(this, customerID);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            senderName = cursor.getString(1);
            nameView.setText(senderName);
            emailView.setText(cursor.getString(2));
            accountnumView.setText("Account No: " + cursor.getString(3));
            currBalance = cursor.getDouble(4);
            balanceView.setText("Balance: Rs. " + currBalance);
            cursor.moveToNext();
        }

        transferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterAmount();
            }
        });
    }

    private void enterAmount() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(CustomerDetailsActivity.this);

        final View enterMoneyLayout = getLayoutInflater().inflate(R.layout.enter_money_layout,null);

        builder.setTitle("Enter amount");
        builder.setView(enterMoneyLayout).setCancelable(false);

        final EditText editText = enterMoneyLayout.findViewById(R.id.enterMoney);

        builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = editText.getText().toString();
                if(amount.isEmpty())
                    editText.setError("This field can't be left blank");
                else if(currBalance < Double.parseDouble(amount))
                    editText.setError("Your account doesn't have enough balance");
                else if(Double.parseDouble(amount) <= 0)
                    editText.setError("Minimum transaction amount is Rs. 1");
                else {
                    Intent transferIntent = new Intent(CustomerDetailsActivity.this, ReceiverListActivity.class);
                    transferIntent.putExtra("senderId", senderId);
                    transferIntent.putExtra("senderName", senderName);
                    transferIntent.putExtra("currBalance", currBalance);
                    transferIntent.putExtra("transferredAmount", Double.parseDouble(amount));
                    startActivity(transferIntent);
                    dialog.dismiss();
//                    finish();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(CustomerDetailsActivity.this, CustomerListActivity.class));
        return super.onOptionsItemSelected(item);
    }
}
