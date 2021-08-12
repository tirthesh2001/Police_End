package com.example.ncrb_police.ui.fir_record;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ncrb_police.FIR_info;
import com.example.ncrb_police.R;
import com.example.ncrb_police.databinding.FragmentFirRecordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class fir_recordFragment extends Fragment implements View.OnClickListener {

    private FragmentFirRecordBinding binding;
    private int mHour, mMinute, mYear, mMonth, mDay;
    String evidence;
    EditText applicant_name,applicant_phone,applicant_email,suspect,time,date,address,statement;
    Button set_time,set_date,submit;
    Calendar c,c1;
    Switch ev;
    TextView ev_pointer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_fir_record,container,false);

        final TextView textView = root.findViewById(R.id.header2);
        ev_pointer = root.findViewById(R.id.ev_pointer);
        //Edit Texts
        applicant_name = root.findViewById(R.id.applicant);
        applicant_phone = root.findViewById(R.id.applicant_phone);
        applicant_email = root.findViewById(R.id.applicant_mail);
        suspect = root.findViewById(R.id.suspect);
        time = root.findViewById(R.id.time);
        date = root.findViewById(R.id.date);
        address = root.findViewById(R.id.add);
        statement = root.findViewById(R.id.statement);

        //Buttons
        set_time = root.findViewById(R.id.set_time);
        set_date = root.findViewById(R.id.set_date);
        submit = root.findViewById(R.id.save_fir);

        //Evidence Set-up with Switch
        ev = root.findViewById(R.id.evidence_swap);

        //On Click Listeners
        set_time.setOnClickListener(this);
        set_date.setOnClickListener(this);
        submit.setOnClickListener(this);
        ev.setOnClickListener(this);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.set_time:
                c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                //Time Picker Launch
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int mins) {
                        time.setText(hour + ":" + mins);
                    }
                },mHour, mMinute, true);
                timePickerDialog.show();
                break;

            case R.id.set_date:
                c1 = Calendar.getInstance();
                mYear = c1.get(Calendar.YEAR);
                mMonth = c1.get(Calendar.MONTH);
                mDay = c1.get(Calendar.DAY_OF_MONTH);

                //Date Picker Launch
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        date.setText(day+"-"+(month+1)+"-"+year);
                    }
                },mDay, mMonth, mYear);
                datePickerDialog.show();
                break;

            case  R.id.evidence_swap:
                if (((Switch)view).isChecked()){
                    ev_pointer.setVisibility(View.VISIBLE);
                    evidence="Yes";
                }
                else {
                    ev_pointer.setVisibility(View.GONE);
                    evidence="No";
                }
                break;

            case  R.id.save_fir:
                save_new_fir();


        }
    }

    private void save_new_fir() {
        String a_name = applicant_name.getText().toString().trim();
        String a_email = applicant_email.getText().toString().trim();
        String a_phone = applicant_phone.getText().toString().trim();
        String a_suspect = suspect.getText().toString().trim();
        String a_time = time.getText().toString().trim();
        String a_date = date.getText().toString().trim();
        String a_area = address.getText().toString().trim();
        String a_statement = statement.getText().toString().trim();
        String a_ev_stat = evidence;
        String a_status = "Disapproved";

        if (a_name.isEmpty()){
            applicant_name.setError("Applicant Name is required");
            applicant_name.requestFocus();
            return;
        }
        if (a_email.isEmpty()){
            applicant_email.setError("Applicant E-mail is required");
            applicant_email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(a_email).matches()){
            applicant_email.setError("Valid E-mail is required");
            applicant_email.requestFocus();
            return;
        }
        if (a_phone.isEmpty()){
            applicant_phone.setError("Applicant Phone No. is required");
            applicant_phone.requestFocus();
            return;
        }
        if (a_phone.length()<10){
            applicant_phone.setError("Valid Phone No. is required");
            applicant_phone.requestFocus();
            return;
        }
        if (a_suspect.isEmpty()){
            a_suspect = "No specified suspect";
            return;
        }
        if (a_time.isEmpty()){
            time.setError("Time of recording is required");
            time.requestFocus();
            return;
        }
        if (a_date.isEmpty()){
            date.setError("Date of recording is required");
            date.requestFocus();
            return;
        }
        if (a_area.isEmpty()){
            address.setError("Locality must be specified");
            address.requestFocus();
            return;
        }
        if (a_statement.isEmpty()){
            statement.setError("Statement of the applicant is required");
            statement.requestFocus();
            return;
        }
        FIR_info fir_info = new FIR_info(a_name,a_email,a_phone,a_suspect,a_time,a_date,a_area,a_statement,a_ev_stat,a_status);
        FirebaseDatabase.getInstance().getReference().child("FIR Records").push().setValue(fir_info).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getContext(), "FIR Registered", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}