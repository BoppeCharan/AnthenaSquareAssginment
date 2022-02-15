package com.charan.anthenasquare.Login;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.charan.anthenasquare.Helpers.AlertPopUps;
import com.charan.anthenasquare.MainActivity;
import com.charan.anthenasquare.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
    EditText edt_fname,edt_email,edt_password;
    Button btn_register;
    TextView tv_alregister;
    FirebaseAuth fAuth;
    ProgressBar pbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signup);

        edt_fname=(EditText)findViewById(R.id.edt_fname);
        edt_email=(EditText)findViewById(R.id.edt_email);
        edt_password=(EditText)findViewById(R.id.edt_password);
        btn_register=(Button)findViewById(R.id.btn_register);
        tv_alregister=(TextView)findViewById(R.id.tv_alregister);
        fAuth= FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        else{
            btn_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email = edt_email.getText().toString().trim();
                    String password= edt_password.getText().toString().trim();

                    if(TextUtils.isEmpty(email)){
                        edt_email.setError("Email is Required");
                        return;
                    }
                    if(TextUtils.isEmpty(password)){
                        edt_password.setError("Password is required");
                        return;
                    }
                    if(password.length()<6){
                        edt_password.setError("Password must be >= 6 Characters");
                        return;
                    }




                    fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                AlertPopUps.showLayoutPopup(R.layout.registration_sucessful_gif,SignUp.this,scanForActivity(SignUp.this));
                                ChangeTheIntent();
                            }
                            else{
                                Toast.makeText(SignUp.this,"Error !"+task.getException().getMessage(),Toast.LENGTH_SHORT);

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUp.this,"Error !"+e.getMessage(),Toast.LENGTH_SHORT);

                        }
                    });
                }
            });
            tv_alregister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(),Login.class));
                }
            });
        }


    }


    private void ChangeTheIntent() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AlertPopUps.dismissPopup();
                startActivity(new Intent(getApplicationContext(), Registration.class));
                finish();
                finishAffinity();
            }
        }, 5000);
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
