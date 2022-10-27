package com.muhammadazeem.redcare;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.muhammadazeem.redcare.R;

public class MainActivity extends AppCompatActivity {

    FirebaseUser authUser = null;
    DatabaseReference reference;
    FirebaseDatabase database;
    LoginSection loginSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(getApplicationContext());
        database = FirebaseDatabase.getInstance();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            //already signed in
            authUser = auth.getCurrentUser();
            onSignIn(authUser);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loginSection = new LoginSection();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainContainer, loginSection);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void onSignIn(FirebaseUser user) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(user.getUid());

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String role = "";
                if (dataSnapshot.exists()) {
                    role = dataSnapshot.child("role").getValue(String.class);
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);


                    if (role.equals("receptionist")) {
                        Intent intent = new Intent(getApplicationContext(), ReceptionMenu.class);
                        intent.putExtra("name", name);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        getFragmentManager().popBackStack();
                    } else if (role.equals("lab_person")) {
                        Intent intent = new Intent(getApplicationContext(), LabPerson.class);
                        intent.putExtra("name", name);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        getFragmentManager().popBackStack();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        reference.addListenerForSingleValueEvent(listener);
    }
}
