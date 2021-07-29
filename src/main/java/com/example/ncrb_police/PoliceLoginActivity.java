package com.example.ncrb_police;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PoliceLoginActivity extends AppCompatActivity {

    EditText email,password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_login);

        email=findViewById(R.id.editTextTextPersonName);
        password=findViewById(R.id.editTextTextPassword2);
        login=findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PoliceLoginActivity.this,MainActivity.class));
            }
        });
    }
}