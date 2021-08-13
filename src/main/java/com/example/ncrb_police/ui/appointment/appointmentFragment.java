package com.example.ncrb_police.ui.appointment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ncrb_police.R;
import com.example.ncrb_police.databinding.FragmentAppointmentBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class appointmentFragment extends Fragment {

    private FragmentAppointmentBinding binding;
    //Declarations
    private RecyclerView approved_app, pending_app;
    appointment_adapter adapter1, adapter2;
    DatabaseReference mdb1,mdb2;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_appointment,container,false);

        approved_app = root.findViewById(R.id.approved_appointments);
        pending_app = root.findViewById(R.id.pending_appointments);

        mdb1 = FirebaseDatabase.getInstance().getReference("Appointment Records");
        mdb2 = FirebaseDatabase.getInstance().getReference("Commoner Appointment Records");

        approved_app.setLayoutManager(new LinearLayoutManager(getContext()));
        pending_app.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Appoint> options1 = new FirebaseRecyclerOptions.Builder<Appoint>().setQuery(mdb1,Appoint.class).build();
        FirebaseRecyclerOptions<Appoint> options2 = new FirebaseRecyclerOptions.Builder<Appoint>().setQuery(mdb2,Appoint.class).build();

        adapter1 = new appointment_adapter(options1);
        adapter2 = new appointment_adapter(options2);

        approved_app.setAdapter(adapter1);
        pending_app.setAdapter(adapter2);

        return root;
    }

    @Override
    public void onStart(){
        super.onStart();
        adapter1.startListening();
        adapter2.startListening();
    }

    @Override
    public void onStop(){
        super.onStop();
        adapter1.stopListening();
        adapter2.stopListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}