package com.example.ncrb_police;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class UserRegister extends AppCompatActivity {

    EditText name,password,number,area,email,l_name;
    Button regis;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        mAuth = FirebaseAuth.getInstance();

        name=findViewById(R.id.u_name);
        password=findViewById(R.id.u_pass);
        number=findViewById(R.id.u_phone);
        area=findViewById(R.id.u_area);
        email=findViewById(R.id.u_email);
        l_name=findViewById(R.id.u_lname);
        regis=findViewById(R.id.Submit);

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }

            public void registerUser() {
                String e_name=name.getText().toString().trim();
                String e_lname=l_name.getText().toString().trim();
                String e_pass=password.getText().toString().trim();
                String e_number=number.getText().toString().trim();
                String e_area=area.getText().toString().trim();
                String e_email=email.getText().toString().trim();

                if(e_name.isEmpty()){
                    name.setError("First Name is required");
                    name.requestFocus();
                    return;
                }
                if (e_lname.isEmpty()){
                    l_name.setError("Last Name is required");
                    l_name.requestFocus();
                    return;
                }
                if (e_pass.isEmpty()){
                    password.setError("Password cannot be empty");
                    password.requestFocus();
                    return;
                }
                if (e_pass.length() < 6){
                    password.setError("Password Should be at least 6 characters");
                    password.requestFocus();
                    return;
                }
                if (e_number.isEmpty()){
                    number.setError("Phone number is required for contact");
                    number.requestFocus();
                    return;
                }
                if (e_number.length()<10){
                    number.setError("Phone number must be valid");
                    number.requestFocus();
                    return;
                }
                if (e_area.isEmpty()){
                    area.setError("Locality is required");
                    area.requestFocus();
                    return;
                }
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
                mAuth.createUserWithEmailAndPassword(e_email,e_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(e_name,e_lname,e_email,e_number,e_area);
                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(UserRegister.this, "User Registered", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(UserRegister.this,PoliceLoginActivity.class));
                                    }else {
                                        Toast.makeText(UserRegister.this, "Task Unsuccessful!! Try Again!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(UserRegister.this, "Task Unsuccessful!!Main Try Again!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}