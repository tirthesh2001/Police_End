package com.example.ncrb_police;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button regis, logout, to_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        regis=findViewById(R.id.register);
        regis.setOnClickListener(this);

        logout=findViewById(R.id.logout);
        logout.setOnClickListener(this);

        to_nav=findViewById(R.id.tonav);
        to_nav.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.register:
                startActivity(new Intent(MainActivity.this,UserRegister.class));
                break;

            case R.id.tonav:
                startActivity(new Intent(MainActivity.this, Navigation_DriverActivity.class));
                break;

            case R.id.logout:
                startActivity(new Intent(MainActivity.this,PoliceLoginActivity.class));
                break;
        }
    }
}