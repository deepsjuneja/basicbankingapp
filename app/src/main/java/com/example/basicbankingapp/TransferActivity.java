package com.example.basicbankingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class TransferActivity extends AppCompatActivity {
    LinearLayout linearLayout, linearLayoutButtons;
    RelativeLayout relativeLayout;
    LottieAnimationView animationView;
    Button homeButton, historyButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer_layout);

        relativeLayout = findViewById(R.id.loadingCircle);
        animationView = findViewById(R.id.animationView);
        linearLayout = findViewById(R.id.afterAnimation);
        linearLayoutButtons = findViewById(R.id.buttonsAfterAnimation);
        homeButton = findViewById(R.id.homeButton);
        historyButton = findViewById(R.id.historyButton);

        animationView.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                relativeLayout.setVisibility(View.GONE);
                animationView.setVisibility(View.VISIBLE);
            }
        }, 4000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                linearLayout.setVisibility(View.VISIBLE);
            }
        }, 5000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                linearLayoutButtons.setVisibility(View.VISIBLE);
            }
        }, 6000);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(TransferActivity.this, HomeActivity.class);
                startActivity(homeIntent);
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyIntent = new Intent(TransferActivity.this, TransactionHistoryActivity.class);
                startActivity(historyIntent);
            }
        });
    }
}
