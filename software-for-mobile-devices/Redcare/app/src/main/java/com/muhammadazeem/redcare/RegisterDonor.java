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

public class RegisterDonor extends Fragment {

    EditText name;
    EditText blood;
    EditText contact;
    EditText address;
    EditText cnic;
    EditText gender;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_donor, container, false);

        Button submit = view.findViewById(R.id.registerDonorBtn);

        name = view.findViewById(R.id.donorName);
        blood = view.findViewById(R.id.donorBlood);
        contact = view.findViewById(R.id.donorContact);
        address = view.findViewById(R.id.donorAddress);
        cnic = view.findViewById(R.id.donorCNIC);
        gender = view.findViewById(R.id.donorGender);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cnic.getText().toString().equals("") || blood.getText().toString().equals("")
                || gender.getText().toString().equals("") || contact.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Fields must not be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                DonorType donor = new DonorType(name.getText().toString(),
                        blood.getText().toString(),
                        contact.getText().toString(),
                        address.getText().toString(),
                        gender.getText().toString());
                FirebaseDatabase.getInstance().getReference("donors" )
                        .child(cnic.getText().toString()).setValue(donor.getMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Donor registered.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }
}
