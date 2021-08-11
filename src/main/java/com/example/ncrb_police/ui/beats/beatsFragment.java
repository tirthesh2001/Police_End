package com.example.ncrb_police.ui.beats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ncrb_police.R;
import com.example.ncrb_police.User;
import com.example.ncrb_police.databinding.FragmentBeatsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class beatsFragment extends Fragment {

    private FragmentBeatsBinding binding;
    Button init_set, print;
    Spinner main_area, user_1, user_2, user_3, user_4, user_5, user_6, user_7;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_beats,container,false);

        //Buttons
        init_set = root.findViewById(R.id.set);
        print = root.findViewById(R.id.print);

        main_area = root.findViewById(R.id.main_area);

        //User Spinners
        user_1 = root.findViewById(R.id.user1);
        user_2 = root.findViewById(R.id.user2);
        user_3 = root.findViewById(R.id.user3);
        user_4 = root.findViewById(R.id.user4);
        user_5 = root.findViewById(R.id.user5);
        user_6 = root.findViewById(R.id.user6);
        user_7 = root.findViewById(R.id.user7);

        List<String> areas = new ArrayList<String>();
        areas.add("Select a locality");
        areas.add("Matunga");
        areas.add("King's Circle");
        areas.add("Wadala");
        areas.add("Five Gardens");
        areas.add("Dadar East");
        areas.add("Sion");

        List<String> police = new ArrayList<String>();

        ArrayAdapter<String> da = new ArrayAdapter<String>(getContext(), R.layout.spinner_item,areas);
        da.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        main_area.setAdapter(da);

        ArrayAdapter<String> for_user = new ArrayAdapter<String>(getContext(),R.layout.spinner_item,police);
        for_user.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        user_1.setAdapter(for_user);
        user_2.setAdapter(for_user);
        user_3.setAdapter(for_user);
        user_4.setAdapter(for_user);
        user_5.setAdapter(for_user);
        user_6.setAdapter(for_user);
        user_7.setAdapter(for_user);

        init_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String choice = main_area.getSelectedItem().toString();
                if (choice.equals("Select a locality")){
                    Toast.makeText(getContext(), "An option has to be selected", Toast.LENGTH_SHORT).show();
                    main_area.requestFocus();
                    return;
                }else {
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for_user.clear();
                            for (DataSnapshot snapshot1 : snapshot.getChildren()){
                                User obj = snapshot1.getValue(User.class);
                                String user_info = obj.getF_name()+" "+obj.getL_name();
                                for_user.add(user_info);
                            }
                            for_user.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    Toast.makeText(getContext(), "Success"+choice, Toast.LENGTH_SHORT).show();
                }
            }
        });
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time1,time2,time3,time4,time5,time6,time7;

                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                time1 = user_1.getSelectedItem().toString();
                time2 = user_2.getSelectedItem().toString();
                time3 = user_3.getSelectedItem().toString();
                time4 = user_4.getSelectedItem().toString();
                time5 = user_5.getSelectedItem().toString();
                time6 = user_6.getSelectedItem().toString();
                time7 = user_7.getSelectedItem().toString();

                Map<String,Object> beats = new HashMap<>();
                beats.put("Date",currentDate);
                beats.put("Time",currentTime);
                beats.put("8:00 am",time1);
                beats.put("11:30 am",time2);
                beats.put("2:30 pm",time3);
                beats.put("5:00 pm",time4);
                beats.put("7:30 pm",time5);
                beats.put("10:30 pm",time6);
                beats.put("2:00 am",time7);

                FirebaseDatabase.getInstance().getReference("Beats").push().setValue(beats).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}