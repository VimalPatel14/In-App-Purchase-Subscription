package com.vimal.inapppurchase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.vimal.inapppurchase.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.inapp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inapp = new Intent(MainActivity.this, InAppActivity.class);
                startActivity(inapp);
            }
        });


        findViewById(R.id.subscription).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inapp = new Intent(MainActivity.this, SubscriptionActivity.class);
                startActivity(inapp);
            }
        });
    }
}