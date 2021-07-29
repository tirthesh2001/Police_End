package com.example.ncrb_police.ui.appointment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ncrb_police.databinding.FragmentAppointmentBinding;

public class appointmentFragment extends Fragment {

    private FragmentAppointmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAppointmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.appoint;
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}