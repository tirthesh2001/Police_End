package com.example.ncrb_police;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ApprovedCaseActivity extends AppCompatActivity {

    TextView display;
    String demo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_case);
        display = findViewById(R.id.textView13);

        Bundle extra = getIntent().getExtras();
        demo = extra.getString("Name");

        display.setText(demo);
    }
}