package com.example.ncrb_police;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class PoliceLoginActivity extends AppCompatActivity {

    EditText email,password;
    Button login;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_login);

        mAuth=FirebaseAuth.getInstance();

        email=findViewById(R.id.editTextTextPersonName);
        password=findViewById(R.id.editTextTextPassword2);
        login=findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }

            private void userLogin() {
                String e_email=email.getText().toString().trim();
                String e_pass=password.getText().toString().trim();

                if (e_email.isEmpty()){
                    email.setError("Email ID is required");
                    email.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(e_email).matches()){
                    email.setError("Enter a valid email");
                    email.requestFocus();
                    return;
                }
                if (e_pass.isEmpty()){
                    password.setError("Password is required");
                    password.requestFocus();
                    return;
                }
                if (e_pass.length() < 6){
                    password.setError("Password should be at least 6 characters");
                    password.requestFocus();
                    return;
                }

                mAuth.signInWithEmailAndPassword(e_email,e_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(PoliceLoginActivity.this,MainActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(PoliceLoginActivity.this, "Login Failed!! Try Again!!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}