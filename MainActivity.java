package com.example.bulletin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btv;
    private Button btp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.btp=(Button)findViewById(R.id.edit);
        this.btv=(Button)findViewById(R.id.view);
        btp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit.start(MainActivity.this);
            }
        });

        btv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vieww.start(MainActivity.this);
            }
        });

    }



}
