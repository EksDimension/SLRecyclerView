package com.eks.slrecyclerview.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnSingleList = findViewById(R.id.btnSingleList);
        btnSingleList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SingleListActivity.class));
            }
        });
        Button btnDoubleList = findViewById(R.id.btnDoubleList);
        btnDoubleList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DoubleListActivity.class));
            }
        });
        Button btnCusLEList = findViewById(R.id.btnCusLEList);
        btnCusLEList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LEListActivity.class));
            }
        });
    }
}
