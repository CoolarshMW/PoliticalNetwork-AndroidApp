package com.project.politicalnetwork.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.project.politicalnetwork.R;

public class VerificationActivity extends AppCompatActivity {

    private Button mVerify;
    private EditText otp;
    private String OTP;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        mVerify = findViewById(R.id.verify);
        otp = findViewById(R.id.otp);

        firebaseAuth = FirebaseAuth.getInstance();

        OTP = getIntent().getStringExtra("auth");
        mVerify.setOnClickListener(v -> {
            String verificationCode = otp.getText().toString();

            if (!verificationCode.isEmpty()) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(OTP, verificationCode);
                signIn(credential);
            }
            else {
                Toast.makeText(this, "Please enter OTP", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signIn(PhoneAuthCredential credential) {

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(VerificationActivity.this, GroupActivity.class));
                }
                else {
                    Toast.makeText(VerificationActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(VerificationActivity.this, GroupActivity.class));
        }
    }
}