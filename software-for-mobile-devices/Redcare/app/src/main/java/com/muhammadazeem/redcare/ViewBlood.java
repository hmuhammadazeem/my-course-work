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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewBlood extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_blood, container, false);

        final ListView list = view.findViewById(R.id.bloodBags_list);
        final ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();


        FirebaseDatabase.getInstance().getReference("blood_bags").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //Getting the data from snapshot

                        BloodType blood = snapshot.getValue(BloodType.class);
                        if (blood.status.equals("accepted")) {
                            HashMap<String, String> map = blood.getMap();
                            data.add(map);
                        } else
                            Toast.makeText(getContext(), "No data available.", Toast.LENGTH_SHORT).show();
                    }
                }
                ListAdapter adapter = new SimpleAdapter(getContext(), data, R.layout.activity_blood_list_helper,
                        new String[]{"bloodGroup", "dateOfEntry"},
                        new int[]{R.id.field1, R.id.field2});
                list.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}
