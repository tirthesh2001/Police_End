package com.example.ncrb_police;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.ncrb_police.ui.appointment.Appoint;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class ApproveAppointmentActivity extends AppCompatActivity {
    //Declarations
    TextView name,user,number,reason,time,date;
    String status,placeholder,opt,demo;
    Button get_time, get_date, approve, disapprove;
    CardView hide_me;
    Calendar c,c1;
    private int mHour, mMinute, mYear, mMonth, mDay;

    DatabaseReference d_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_appointment);

        //Text Views Linking
        name = findViewById(R.id.full_name);
        user = findViewById(R.id.appoint_with);
        number = findViewById(R.id.phone_number);
        reason = findViewById(R.id.reason_disp);
        time = findViewById(R.id.me_time);
        date = findViewById(R.id.me_date);

        //Button Linking
        get_date = findViewById(R.id.get_date);
        get_time = findViewById(R.id.get_time);
        approve = findViewById(R.id.approve);
        disapprove = findViewById(R.id.disapprove);

        //Card View Link
        hide_me = findViewById(R.id.hide_if_required);

        Bundle extra = getIntent().getExtras();
        demo = extra.getString("Reason");
        opt = extra.getString("Status");

        if (opt.equals("Approved")){
            d_ref = FirebaseDatabase.getInstance().getReference("Appointment Records");
        }
        else {
            d_ref = FirebaseDatabase.getInstance().getReference("Commoner Appointment Records");
        }
        d_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    Appoint a1 = ds.getValue(Appoint.class);
                    placeholder = a1.getReason();
                    if (placeholder.equals(demo)){
                        name.setText(a1.getCommoner_name());
                        user.setText(a1.getUser());
                        number.setText(a1.getCommoner_no());
                        reason.setText(a1.getReason());
                        status=a1.getStatus();
                        if (status.equals("Approved")){
                            hide_me.setVisibility(View.GONE);
                        }
                        else{
                            get_time.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    c = Calendar.getInstance();
                                    mHour = c.get(Calendar.HOUR_OF_DAY);
                                    mMinute = c.get(Calendar.MINUTE);

                                    //Time Picker Launch
                                    TimePickerDialog timePickerDialog = new TimePickerDialog(ApproveAppointmentActivity.this, new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(TimePicker timePicker, int hour, int mins) {
                                            time.setText(hour + ":" + mins);
                                        }
                                    },mHour, mMinute, true);
                                    timePickerDialog.show();
                                }
                            });
                            get_date.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    c1 = Calendar.getInstance();
                                    mYear = c1.get(Calendar.YEAR);
                                    mMonth = c1.get(Calendar.MONTH);
                                    mDay = c1.get(Calendar.DAY_OF_MONTH);

                                    //Date Picker Launch
                                    DatePickerDialog datePickerDialog = new DatePickerDialog(ApproveAppointmentActivity.this, new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                            date.setText(day+"-"+(month+1)+"-"+year);
                                        }
                                    },mDay, mMonth, mYear);
                                    datePickerDialog.show();
                                }
                            });
                            approve.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    status = "Approved";
                                    Appoint appointment = new Appoint(name.getText().toString(),number.getText().toString(),
                                            reason.getText().toString(),time.getText().toString(),date.getText().toString(),
                                            user.getText().toString(),status);
                                    FirebaseDatabase.getInstance().getReference("Appointment Records").push().setValue(appointment).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                                                ds.getRef().removeValue();
                                            }else{
                                                Toast.makeText(getApplicationContext(), "Not Done", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                    FirebaseDatabase.getInstance().getReference("Approved Appointment").push().setValue(appointment).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(getApplicationContext(), "Not Done", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });
                            disapprove.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Appoint appointment = new Appoint(name.getText().toString(),number.getText().toString(),
                                            reason.getText().toString(),time.getText().toString(),date.getText().toString(),
                                            user.getText().toString(),status);
                                    FirebaseDatabase.getInstance().getReference("Disapproved Appointment Records").push().setValue(appointment).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                                                ds.getRef().removeValue();
                                            }else{
                                                Toast.makeText(getApplicationContext(), "Not Done", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}