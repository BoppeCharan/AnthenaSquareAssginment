package com.charan.anthenasquare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.widget.Toast;

import com.charan.anthenasquare.Entites.Student;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private boolean doubleBackToExitPressedOnce = false;
    FirebaseDatabase database;
    DatabaseReference usersDatabase;
    String uid;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.home_activity);

        fAuth = FirebaseAuth.getInstance();
        uid = fAuth.getUid();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        database = FirebaseDatabase.getInstance();
        usersDatabase = database.getReference("Students");

        usersDatabase = database.getReference("Students").child(uid);
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new Profile()).commit();


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.profile){
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new Profile()).commit();
                    return true;
                }
                else if( item.getItemId() == R.id.group){
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new Group()).commit();
                    return true;
                }
                return false;
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}