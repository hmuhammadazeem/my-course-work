package com.muhammadazeem.redcare;

import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class LabPerson extends AppCompatActivity {

    final ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_person);

        final ListView list = findViewById(R.id.labList);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int myId = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("Verify Blood").setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String cnId = data.get(myId).get("id");
                        FirebaseDatabase.getInstance().getReference("blood_bags").child(cnId)
                                .child("status").setValue("accepted");
                    }
                }).setNegativeButton("Reject", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String cnId = data.get(myId).get("id");
                        FirebaseDatabase.getInstance().getReference("blood_bags").child(cnId)
                                .setValue(null);
                    }
                });
                builder.show();
            }
        });

        FirebaseDatabase.getInstance().getReference("blood_bags").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //Getting the data from snapshot
                        if (snapshot.child("status").getValue(String.class).equals("pending")) {
                            HashMap<String, String> map = new HashMap<>();
                            map.put("id", snapshot.getKey());
                            map.put("bloodGroup", snapshot.child("bloodGroup").getValue(String.class));
                            map.put("dateOfEntry", snapshot.child("dateOfEntry").getValue(String.class));
                            data.add(map);
                        }
                        }
                        ListAdapter adapter = new SimpleAdapter(getApplicationContext(), data, R.layout.activity_lab_list_helper,
                                new String[]{"id", "bloodGroup", "dateOfEntry"},
                                new int[]{R.id.field1, R.id.field2, R.id.field3});
                        list.setAdapter(adapter);
                }
                else
                    Toast.makeText(getApplicationContext(), "No data available.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseAuth.getInstance().signOut();
    }
}
