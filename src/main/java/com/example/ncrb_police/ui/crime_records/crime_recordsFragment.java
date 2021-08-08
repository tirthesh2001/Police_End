package com.example.ncrb_police.ui.crime_records;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ncrb_police.R;
import com.example.ncrb_police.databinding.FragmentCrimeRecordsBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class crime_recordsFragment extends Fragment {

    private FragmentCrimeRecordsBinding binding;
    private RecyclerView recyclerView, for_user;
    c_case_adapter adapter,adapter1; // Create Object of the Adapter class
    DatabaseReference mbase,mbase1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_crime_records,container,false);

        mbase = FirebaseDatabase.getInstance().getReference("FIR Records");
        mbase1 = FirebaseDatabase.getInstance().getReference("Commoner Records");

        recyclerView = root.findViewById(R.id.recycle_approved);
        for_user = root.findViewById(R.id.recycle_commoner);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        for_user.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<crime_cases> options = new FirebaseRecyclerOptions.Builder<crime_cases>().setQuery(mbase,crime_cases.class).build();
        FirebaseRecyclerOptions<crime_cases> options1 = new FirebaseRecyclerOptions.Builder<crime_cases>().setQuery(mbase1,crime_cases.class).build();

        adapter = new c_case_adapter(options);
        adapter1 = new c_case_adapter(options1);

        recyclerView.setAdapter(adapter);
        for_user.setAdapter(adapter1);

        return root;
    }

    @Override
    public void onStart(){
        super.onStart();
        adapter.startListening();
        adapter1.startListening();
    }

    @Override
    public void onStop(){
        super.onStop();
        adapter.stopListening();
        adapter1.stopListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}