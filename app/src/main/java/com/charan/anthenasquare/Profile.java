package com.charan.anthenasquare;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.charan.anthenasquare.Adapters.InternAdapter;
import com.charan.anthenasquare.Adapters.SingleValueAdapter;
import com.charan.anthenasquare.Adapters.SkillAdapter;
import com.charan.anthenasquare.Entites.Intern;
import com.charan.anthenasquare.Entites.Student;
import com.charan.anthenasquare.Helpers.AlertPopUps;
import com.charan.anthenasquare.Helpers.StudentDataService;
import com.charan.anthenasquare.Helpers.ValidationIndiactors;
import com.charan.anthenasquare.databinding.FragmentProfileBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Profile extends Fragment {


    FragmentProfileBinding mBinding;
    FirebaseDatabase database;
    DatabaseReference usersDatabase;
    String uid;
    FirebaseAuth fAuth;
    Student student;
    StudentDataService studentDataService;
    SingleValueAdapter badgesAdapter;
    SkillAdapter skillAdapter;
    InternAdapter internAdapter;
    List<String> badgeList,skillList;
    List<Intern> internList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentProfileBinding.inflate(inflater,container,false);


        fAuth = FirebaseAuth.getInstance();
        uid = fAuth.getUid();
        studentDataService = StudentDataService.getInstance();

        database = FirebaseDatabase.getInstance();
        usersDatabase = database.getReference("Students");

        usersDatabase = database.getReference("Students").child(uid);

        usersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    student = snapshot.getValue(Student.class);
                    UpdateUI(student);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mBinding.addBadge.setOnClickListener( v -> {
            badgeList = student.getBadges();
            View V = AlertPopUps.showLayoutPopup(R.layout.add_item,requireContext(),requireActivity());
            TextInputEditText value = V.findViewById(R.id.itemRes);
            TextInputLayout layout = V.findViewById(R.id.itemIL);
            Button addValue = V.findViewById(R.id.addValue);

            addValue.setOnClickListener(ve -> {
                String valueRes = value.getText().toString();
                if(valueRes.length() < 0){
                    ValidationIndiactors.showErrorIndicatorAfterBtnPressed(value,"Required",layout);
                }
                else{
                    badgeList.add(valueRes);
                    student.setBadges(badgeList);
                    studentDataService.updateStudent(student,uid);
                    badgesAdapter = new SingleValueAdapter(badgeList);
                    mBinding.badgesView.setLayoutManager(new LinearLayoutManager(requireContext()));
                    mBinding.badgesView.setAdapter(badgesAdapter);
                    AlertPopUps.dismissPopup();
                }
            });

        });


        mBinding.addSkill.setOnClickListener( v -> {
            View V = AlertPopUps.showLayoutPopup(R.layout.add_item,requireContext(),requireActivity());
            TextInputEditText value = V.findViewById(R.id.itemRes);
            TextInputLayout layout = V.findViewById(R.id.itemIL);
            Button addValue = V.findViewById(R.id.addValue);
            addValue.setOnClickListener(ve -> {
                String valueRes = value.getText().toString();
                if(valueRes.length() < 0){
                    ValidationIndiactors.showErrorIndicatorAfterBtnPressed(value,"Required",layout);
                }
                else{
                    skillList.add(valueRes);
                    student.setSkills(skillList);
                    studentDataService.updateStudent(student,uid);
                    badgesAdapter = new SingleValueAdapter(badgeList);
                    mBinding.badgesView.setLayoutManager(new LinearLayoutManager(requireContext()));
                    mBinding.badgesView.setAdapter(badgesAdapter);
                    AlertPopUps.dismissPopup();
                }
            });

        });

        mBinding.addExp.setOnClickListener( v -> {
            startActivity(new Intent(requireContext(),AddExperienceActivity.class));
        });


        return mBinding.getRoot();


    }

    private void UpdateUI(Student s) {
        mBinding.displayName.setText(s.getName());
        mBinding.collegeName.setText(s.getCollegeName());
        mBinding.departmentName.setText(s.getDepartment());
        badgeList = s.getBadges();
        skillList = s.getSkills();
        internList = s.getInternships();

        if(badgeList.size() >= 1){
            mBinding.noBadges.setVisibility(View.GONE);
            mBinding.noBadgeImage.setVisibility(View.GONE);
            mBinding.badgesView.setVisibility(View.VISIBLE);
            badgesAdapter = new SingleValueAdapter(badgeList);
            mBinding.badgesView.setLayoutManager(new LinearLayoutManager(Profile.this.getContext()));
            mBinding.badgesView.setAdapter(badgesAdapter);
        }

        if(skillList.size() >= 1){
            mBinding.noSkill.setVisibility(View.GONE);
            mBinding.noSkillImage.setVisibility(View.GONE);
            mBinding.skillView.setVisibility(View.VISIBLE);
            skillAdapter = new SkillAdapter(skillList);
            mBinding.skillView.setLayoutManager(new LinearLayoutManager(Profile.this.getContext()));
            mBinding.skillView.setAdapter(skillAdapter);
        }

        if(internList.size() >= 1){
            mBinding.noExp.setVisibility(View.GONE);
            mBinding.noExpImage.setVisibility(View.GONE);
            mBinding.expView.setVisibility(View.VISIBLE);
            internAdapter = new InternAdapter(internList);
            mBinding.expView.setLayoutManager(new LinearLayoutManager(Profile.this.getContext()));
            mBinding.expView.setAdapter(internAdapter);
        }

    }
}
