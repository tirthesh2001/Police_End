package com.example.ncrb_police.ui.appointment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ncrb_police.R;
import com.example.ncrb_police.databinding.FragmentAppointmentBinding;

public class appointmentFragment extends Fragment {

    private FragmentAppointmentBinding binding;
    //Declarations
    RecyclerView approved_app, pending_app;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_appointment,container,false);

        approved_app = root.findViewById(R.id.approved_appointments);
        pending_app = root.findViewById(R.id.pending_appointments);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}