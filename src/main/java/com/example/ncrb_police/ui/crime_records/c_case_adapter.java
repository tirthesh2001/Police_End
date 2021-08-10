package com.example.ncrb_police.ui.crime_records;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ncrb_police.ApprovedCaseActivity;
import com.example.ncrb_police.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class c_case_adapter extends FirebaseRecyclerAdapter<
        crime_cases, c_case_adapter.caseViewholder>{

    Context context;
    public c_case_adapter(
            @NonNull FirebaseRecyclerOptions<crime_cases> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull c_case_adapter.caseViewholder holder, int position, @NonNull crime_cases model) {
        holder.fname.setText(model.getName());
        holder.email.setText(model.getEmail());
        holder.statement.setText(model.getStatement());
        holder.lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String send_txt1 = model.getStatement();
                String send_txt2 = model.getStatus();
                Intent i = new Intent(context, ApprovedCaseActivity.class);
                i.putExtra("Statement",send_txt1);
                i.putExtra("Choice",send_txt2);
                context.startActivity(i);
            }
        });
    }

    @NonNull
    @Override
    public c_case_adapter.caseViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.crime_case,parent,false);
        return new c_case_adapter.caseViewholder(view);
    }

    public class caseViewholder extends RecyclerView.ViewHolder {
        TextView fname,email,statement;
        CardView lay;
        public caseViewholder (@NonNull View itemView){
            super(itemView);
            context = itemView.getContext();
            fname = itemView.findViewById(R.id.firstname);
            email = itemView.findViewById(R.id.email_id);
            statement = itemView.findViewById(R.id.case_state);
            lay = itemView.findViewById(R.id.getclick);
        }
    }
}
