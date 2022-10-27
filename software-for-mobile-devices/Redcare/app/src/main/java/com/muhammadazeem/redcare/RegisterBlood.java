package com.muhammadazeem.redcare;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterBlood extends Fragment {

    EditText cnic;
    EditText group;
    EditText entry;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_blood, container, false);

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        Button submit = view.findViewById(R.id.registerBloodBtn);
        cnic = view.findViewById(R.id.bloodDonorCNIC);
        group = view.findViewById(R.id.bloodBagGroup);
        entry = view.findViewById(R.id.dateOfEntry);

        entry.setText(formattedDate);
        entry.setEnabled(false);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cnic.getText().toString().equals("") || group.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Fields must not be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }


                BloodType bag = new BloodType(group.getText().toString(),
                        entry.getText().toString());
                FirebaseDatabase.getInstance().getReference("blood_bags")
                        .child(cnic.getText().toString()).setValue(bag.getMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Blood bag registered.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



        return view;
    }
}
