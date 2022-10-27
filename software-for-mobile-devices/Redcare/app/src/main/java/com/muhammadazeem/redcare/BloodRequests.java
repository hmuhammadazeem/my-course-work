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

public class BloodRequests extends Fragment {

    final ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.blood_requests, container, false);

        final ListView list = view.findViewById(R.id.requestList);


        FirebaseDatabase.getInstance().getReference("blood_requests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //Getting the data from snapshot
                        HashMap<String, String> map = new HashMap<>();
                        map.put("name", snapshot.child("name").getValue(String.class));
                        map.put("contact", snapshot.child("contact").getValue(String.class));
                        map.put("group", snapshot.child("group").getValue(String.class));
                        map.put("location", snapshot.child("location").getValue(String.class));
                        data.add(map);
                    }
                    ListAdapter adapter = new SimpleAdapter(getContext(),
                            data, R.layout.activity_blood_request_list_helper,
                            new String[]{"name", "contact", "group", "location"},
                            new int[]{R.id.textView, R.id.textView2, R.id.textView3, R.id.textView4});
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
