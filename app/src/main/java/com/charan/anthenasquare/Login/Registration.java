package com.charan.anthenasquare.Login;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.charan.anthenasquare.Entites.Student;
import com.charan.anthenasquare.Helpers.AlertPopUps;
import com.charan.anthenasquare.Helpers.ValidationIndiactors;
import com.charan.anthenasquare.MainActivity;
import com.charan.anthenasquare.R;
import com.charan.anthenasquare.databinding.RegistrationActivityBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {


    RegistrationActivityBinding mBinding;
    FirebaseAuth fAuth;
    String uid;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        mBinding = RegistrationActivityBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        fAuth = FirebaseAuth.getInstance();

        uid = fAuth.getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


        mBinding.registrationContinue.setOnClickListener( v -> {

            if(CheckAllFields()){
                AlertPopUps.showLayoutPopup(R.layout.registration_sucessful_gif,Registration.this,scanForActivity(Registration.this));
                String name = mBinding.nameRes.getText().toString();
                String collegeName = mBinding.collegeRes.getText().toString();
                String deptName = mBinding.deptRes.getText().toString();
                String year = mBinding.yearRes.getText().toString();
                Student s = new Student();
                s.setName(name);
                s.setCollegeName(collegeName);
                s.setDepartment(deptName);
                s.setYear(year);


                databaseReference.child("Students").child(uid).setValue(s).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(Registration.this, MainActivity.class));
                            AlertPopUps.dismissPopup();
                            finish();
                        }
                        else{
                            AlertPopUps.dismissPopup();
                            Toast.makeText(Registration.this, "Failed Dude!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        AlertPopUps.dismissPopup();
                        Toast.makeText(Registration.this, "Failed Dude!!", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


        mBinding.backBtn.setOnClickListener( v -> onBackPressed());

    }

    private boolean CheckAllFields() {
        boolean error = true;
        if (mBinding.nameRes.length() == 0) {
            ValidationIndiactors.showErrorIndicatorAfterBtnPressed(mBinding.nameRes, "Required", mBinding.nameIL);
            error = false;
        }

        if (mBinding.collegeRes.length() == 0) {
            ValidationIndiactors.showErrorIndicatorAfterBtnPressed(mBinding.collegeRes, "Required", mBinding.collegeIL);
            error = false;
        }

        if (mBinding.deptRes.length() == 0) {
            ValidationIndiactors.showErrorIndicatorAfterBtnPressed(mBinding.deptRes, "Required", mBinding.depIL);
            error = false;
        }

        if (mBinding.yearRes.length() == 0) {
            ValidationIndiactors.showErrorIndicatorAfterBtnPressed(mBinding.yearRes, "Required", mBinding.yearIL);
            error = false;
        }
        return error;
    }

    private static Activity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof Activity)
            return (Activity)cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper)cont).getBaseContext());
        return null;
    }
}
