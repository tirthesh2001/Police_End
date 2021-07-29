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
import com.example.ncrb_police.databinding.FragmentBeatsBinding;

import java.util.ArrayList;
import java.util.List;

public class beatsFragment extends Fragment {

    private FragmentBeatsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBeatsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView textView = binding.textBeats;
        Button init_set = binding.set;
        Spinner main_area = binding.mainArea;
        List<String> areas = new ArrayList<String>();
        areas.add("Matunga");
        areas.add("King's Circle");
        areas.add("Wadala");
        areas.add("Five Gardens");
        areas.add("Dadar East");
        areas.add("Sion");

        ArrayAdapter<String> da = new ArrayAdapter<String>(getContext(), R.layout.spinner_item,areas);
        da.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        main_area.setAdapter(da);

        init_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String choice = main_area.getSelectedItem().toString();
                Toast.makeText(getContext(), choice, Toast.LENGTH_SHORT).show();
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