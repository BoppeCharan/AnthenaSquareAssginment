package com.charan.anthenasquare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.charan.anthenasquare.Entites.Student;
import com.charan.anthenasquare.Helpers.AlertPopUps;
import com.charan.anthenasquare.Helpers.StudentDataService;
import com.charan.anthenasquare.Login.SignUp;
import com.charan.anthenasquare.databinding.FragmentGroupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

import java.util.concurrent.Executor;

public class Group extends Fragment {

    FragmentGroupBinding mBinding;
    FirebaseDatabase database;
    DatabaseReference usersDatabase;
    String uid;
    FirebaseAuth fAuth;
    Student student;
    StudentDataService studentDataService;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentGroupBinding.inflate(inflater, container, false);

        fAuth = FirebaseAuth.getInstance();
        uid = fAuth.getUid();
        studentDataService = StudentDataService.getInstance();

        database = FirebaseDatabase.getInstance();
        usersDatabase = database.getReference("Students");

        usersDatabase = database.getReference("Students").child(uid);

        usersDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
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


        mBinding.referAndEarn.setOnClickListener( v -> createLink());

        mBinding.logout.setOnClickListener( v -> {
            logoutPopup();
        });



        return mBinding.getRoot();
    }

    private void logoutPopup() {

        View view = AlertPopUps.showLayoutPopup(R.layout.logout_popup,requireContext(),requireActivity());

        final Button cancel = view.findViewById(R.id.cancel);
        final Button logout = view.findViewById(R.id.logout);

        cancel.setOnClickListener(V ->{
            AlertPopUps.dismissPopup();
        });

        logout.setOnClickListener(V->{
            fAuth.signOut();
            Intent i = new Intent(requireContext(), SignUp.class);
            startActivity(i);
            requireActivity().finish();
            requireActivity().finishAffinity();
        });
    }

    private void UpdateUI(Student student) {

        mBinding.collegeRes.setText(student.getCollegeName());
        mBinding.deptRes.setText(student.getDepartment());
        mBinding.yearRes.setText(student.getYear());

    }

    public void createLink(){

        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://charan-boppe.netlify.app/.com/"))
                .setDomainUriPrefix("https://anthenasquare.page.link/")
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                .setIosParameters(new DynamicLink.IosParameters.Builder("com.example.ios").build())
                .buildDynamicLink();

        Uri dynamicLinkUri = dynamicLink.getUri();
        shortenLink(dynamicLinkUri.toString());
    }

    public void shortenLink(String longLink){
        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLongLink(Uri.parse(longLink))
                .buildShortDynamicLink()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            // Short link created
                            Uri shortLink = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();


                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_SEND);
                            intent.putExtra(Intent.EXTRA_TEXT,shortLink.toString());
                            intent.setType("text/pain");
                            startActivity(intent);
                        } else {
                            Log.d("ShortLink : ", "Failed to get the short Link" );
                        }
                    }
                });
    }
}
