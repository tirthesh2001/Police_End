package com.example.ncrb_police.ui.beats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ncrb_police.R;
import com.example.ncrb_police.User;
import com.example.ncrb_police.databinding.FragmentBeatsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class beatsFragment extends Fragment {

    private FragmentBeatsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBeatsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView textView = binding.textBeats;

        //Buttons
        Button init_set = binding.set;
        Button print = binding.print;

        Spinner main_area = binding.mainArea;

        //User Spinners
        Spinner user_1 = binding.user1;
        Spinner user_2 = binding.user2;
        Spinner user_3 = binding.user3;
        Spinner user_4 = binding.user4;
        Spinner user_5 = binding.user5;
        Spinner user_6 = binding.user6;
        Spinner user_7 = binding.user7;

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

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}