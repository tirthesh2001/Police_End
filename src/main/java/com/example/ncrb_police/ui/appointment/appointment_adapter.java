package com.example.ncrb_police.ui.appointment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ncrb_police.ApproveAppointmentActivity;
import com.example.ncrb_police.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class appointment_adapter extends FirebaseRecyclerAdapter<
        Appoint, appointment_adapter.AppointViewholder>{

    Context context;

    public appointment_adapter(
            @NonNull FirebaseRecyclerOptions<Appoint> options)
    {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull appointment_adapter.AppointViewholder holder, int position, @NonNull Appoint model) {
        holder.name.setText(model.getCommoner_name());
        holder.number.setText(model.getCommoner_no());
        holder.reason.setText(model.getReason());
        holder.lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String send_txt1 = model.getReason();
                String send_txt2 = model.getStatus();
                Intent i = new Intent(context, ApproveAppointmentActivity.class);
                i.putExtra("Reason",send_txt1);
                i.putExtra("Status",send_txt2);
                context.startActivity(i);
            }
        });
    }

    @NonNull
    @Override
    public appointment_adapter.AppointViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appoint,parent,false);
        return new appointment_adapter.AppointViewholder(view);
    }

    public class AppointViewholder extends RecyclerView.ViewHolder {
        TextView name,number,reason;
        CardView lay;
        public AppointViewholder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();

            name = itemView.findViewById(R.id.full_name);
            number = itemView.findViewById(R.id.phone_no);
            reason = itemView.findViewById(R.id.appoint_reason);

            lay = itemView.findViewById(R.id.take_click);
        }
    }
}
