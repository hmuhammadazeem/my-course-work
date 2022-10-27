package com.muhammadazeem.redcare;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewDonors extends Fragment {

    final ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_donors, container, false);

        final ListView list = view.findViewById(R.id.donorList);

        FirebaseDatabase.getInstance().getReference("donors").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //Getting the data from snapshot
                        DonorType donor = snapshot.getValue(DonorType.class);
                        HashMap<String, String> map = donor.getMap();
                        data.add(map);
                    }
                    ListAdapter adapter = new SimpleAdapter(getContext(), data, R.layout.activity_donor_list_helper,
                            new String[]{"name", "bloodGroup", "contact", "address", "gender"},
                            new int[]{R.id.field1, R.id.field2, R.id.field3, R.id.field4, R.id.field5});
                    list.setAdapter(adapter);
                }
                else
                    Toast.makeText(getContext(), "No data available.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}
