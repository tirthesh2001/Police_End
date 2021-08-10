package com.example.ncrb_police;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ApprovedCaseActivity extends AppCompatActivity {

    //Information TextViews
    TextView name,email,phone,suspect,time,date,locality,f_statement,evidence_avl;
    //Useless Strings
    String status,sample,demo,option;
    Button approve,disapprove;
    CardView hide_me;

    DatabaseReference dr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_case);

        //TextView Linking
        name=findViewById(R.id.full_name);
        email=findViewById(R.id.email_add);
        phone=findViewById(R.id.phone_number);
        suspect=findViewById(R.id.suspect_name);
        time=findViewById(R.id.time_disp);
        date=findViewById(R.id.date_disp);
        locality=findViewById(R.id.area_disp);
        f_statement=findViewById(R.id.state_disp);
        evidence_avl=findViewById(R.id.evidence_status);

        //Action Button Links
        approve=findViewById(R.id.approve);
        disapprove=findViewById(R.id.disapprove);

        hide_me=findViewById(R.id.hide_if_required);

        Bundle extra = getIntent().getExtras();
        demo = extra.getString("Statement");
        option = extra.getString("Choice");

        if (option.equals("Approved")){
            dr = FirebaseDatabase.getInstance().getReference("FIR Records");
        }else{
            dr = FirebaseDatabase.getInstance().getReference("Commoner Records");
        }

        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    FIR_info a1 = ds.getValue(FIR_info.class);
                    sample = a1.getStatement();
                    if (sample.equals(demo)){
                        name.setText(a1.getName());
                        email.setText(a1.getEmail());
                        phone.setText(a1.getPhone());
                        suspect.setText(a1.getSuspect());
                        time.setText(a1.getTime());
                        date.setText(a1.getDate());
                        f_statement.setText(a1.getStatement());
                        locality.setText(a1.getArea());
                        evidence_avl.setText(a1.getEvid());
                        status=a1.getStatus();
                        if (status.equals("Approved")){
                            hide_me.setVisibility(View.GONE);
                        }else {
                            Toast.makeText(getApplicationContext(), "Hello"+status, Toast.LENGTH_SHORT).show();
                            approve.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    status="Approved";
                                    FIR_info put_data = new FIR_info(name.getText().toString(),email.getText().toString(),phone.getText().toString(),suspect.getText().toString(),
                                            time.getText().toString(),date.getText().toString(),locality.getText().toString(),f_statement.getText().toString(),evidence_avl.getText().toString(),status);
                                    FirebaseDatabase.getInstance().getReference("FIR Records").push().setValue(put_data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                                                ds.getRef().removeValue();
                                            }else {
                                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });
                            disapprove.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ds.getRef().removeValue();
                                }
                            });
                        }
                        break;
                    }
                    else{
                        continue;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Toast.makeText(getApplicationContext(), "this"+status, Toast.LENGTH_SHORT).show();
    }
}