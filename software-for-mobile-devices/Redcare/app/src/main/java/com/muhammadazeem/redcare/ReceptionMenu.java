package com.muhammadazeem.redcare;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ReceptionMenu extends AppCompatActivity {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            boolean success = false;
            getSupportFragmentManager().popBackStack();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentTransaction.replace(R.id.viewContainer, new RegisterDonor());
                    success = true;
                    break;
                case R.id.navigation_dashboard:
                    fragmentTransaction.replace(R.id.viewContainer, new RegisterBlood());
                    success = true;
                    break;
                case R.id.navigation_notifications:
                    fragmentTransaction.replace(R.id.viewContainer, new BloodRequests());
                    success = true;
                    break;
                case R.id.view_donors:
                    fragmentTransaction.replace(R.id.viewContainer, new ViewDonors());
                    success = true;
                    break;
                case R.id.view_blood:
                    fragmentTransaction.replace(R.id.viewContainer, new ViewBlood());
                    success = true;
                    break;
            }

            if (success) {
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reception_menu);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseAuth.getInstance().signOut();
    }
}
