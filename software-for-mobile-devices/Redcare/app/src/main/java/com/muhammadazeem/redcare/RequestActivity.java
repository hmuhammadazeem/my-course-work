package com.muhammadazeem.redcare;

import android.location.LocationListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
    }

    public void submitRequest(View view) {
        EditText name;
        EditText contact;
        EditText group;
        EditText location;

        name = findViewById(R.id.reqName);
        contact = findViewById(R.id.reqContact);
        group = findViewById(R.id.reqGroup);
        location = findViewById(R.id.reqLocation);

        if(name.getText().toString().equals("") || contact.getText().toString().equals("")){
            Toast.makeText(this, "Fields must not be empty.", Toast.LENGTH_SHORT);
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("name", name.getText().toString());
        map.put("contact", contact.getText().toString());
        map.put("group", contact.getText().toString());
        map.put("location", location.getText().toString());

        FirebaseDatabase.getInstance().getReference("blood_requests").child(map.get("contact")).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Request submitted.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void getLocation(View view) {

    }
}
