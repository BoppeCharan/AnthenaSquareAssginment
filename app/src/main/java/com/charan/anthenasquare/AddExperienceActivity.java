package com.charan.anthenasquare;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.charan.anthenasquare.Entites.Intern;
import com.charan.anthenasquare.Entites.Student;
import com.charan.anthenasquare.Helpers.StudentDataService;
import com.charan.anthenasquare.Helpers.ValidationIndiactors;
import com.charan.anthenasquare.databinding.AddExpActivityBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.List;

public class AddExperienceActivity extends AppCompatActivity {

    AddExpActivityBinding mBinding;
    List<Intern> internList;
    FirebaseDatabase database;
    DatabaseReference usersDatabase;
    String uid;
    FirebaseAuth fAuth;
    Student student;
    StudentDataService studentDataService;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        mBinding = AddExpActivityBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        studentDataService = StudentDataService.getInstance();


        fAuth = FirebaseAuth.getInstance();
        uid = fAuth.getUid();
        database = FirebaseDatabase.getInstance();
        usersDatabase = database.getReference("Students");

        usersDatabase = database.getReference("Students").child(uid);

        usersDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    student = snapshot.getValue(Student.class);
                    internList = student.getInternships();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        mBinding.backBtn.setOnClickListener(v -> onBackPressed());

        mBinding.addIntern.setOnClickListener( v -> {
            if(CheckAllFields()){
                String compName = mBinding.nameRes.getText().toString();
                String role = mBinding.roleRes.getText().toString();
                String startdate = mBinding.startRes.getText().toString();
                String enddate = mBinding.endRes.getText().toString();
                String desp = mBinding.despRes.getText().toString();

                Intern intern = new Intern(compName,role,startdate,enddate,desp);

                internList = student.getInternships();
                internList.add(intern);
                student.setInternships(internList);

                studentDataService.updateStudent(student,uid);

                onBackPressed();
                finish();


            }
        });


        mBinding.endRes.setOnClickListener( v -> {
            handleDateButton(mBinding.endRes);
        });

        mBinding.startRes.setOnClickListener( v -> {
            handleDateButton(mBinding.startRes);
        });





    }


    private boolean CheckAllFields() {
        boolean error = true;
        if (mBinding.nameRes.length() == 0) {
            ValidationIndiactors.showErrorIndicatorAfterBtnPressed(mBinding.nameRes, "Required", mBinding.nameIL);
            error = false;
        }

        if (mBinding.roleRes.length() == 0) {
            ValidationIndiactors.showErrorIndicatorAfterBtnPressed(mBinding.roleRes, "Required", mBinding.roleIL);
            error = false;
        }

        if (mBinding.startRes.length() == 0) {
            ValidationIndiactors.showErrorIndicatorAfterBtnPressed(mBinding.startRes, "Required", mBinding.startDateIL);
            error = false;
        }

        if (mBinding.endRes.length() == 0) {
            ValidationIndiactors.showErrorIndicatorAfterBtnPressed(mBinding.endRes, "Required", mBinding.endDateIl);
            error = false;
        }
        if(mBinding.despRes.length() == 0){
            ValidationIndiactors.showErrorIndicatorAfterBtnPressed(mBinding.despRes, "Required", mBinding.desIL);
            error = false;
        }
        return error;
    }



    private void handleDateButton(TextInputEditText date_view){
        Calendar calendar = Calendar.getInstance();
        int Year = calendar.get(Calendar.YEAR);
        int Month = calendar.get(Calendar.MONTH);
        int Date = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR , year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE , date);

                CharSequence dateCharSequence = DateFormat.format("MMM d, yyyy" , calendar1);
                date_view.setText(dateCharSequence);
            }
        } , Year , Month , Date);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }
}
