package com.project.politicalnetwork.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.project.politicalnetwork.R;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    EditText phone;
    Button login;
    TextView register_tv;
    private FirebaseAuth auth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phone = findViewById(R.id.phone);
        login = findViewById(R.id.otpButton);
        register_tv = findViewById(R.id.login_tv);

        auth = FirebaseAuth.getInstance();

        login.setOnClickListener(v -> {

            String phone_no = phone.getText().toString();
            String completePhoneNumber = "+91" + "" + phone_no;

            if (phone_no.isEmpty()) {
                Toast.makeText(this, "Please enter valid Phone Number", Toast.LENGTH_SHORT).show();
            }
            else {
                PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(completePhoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(LoginActivity.this)
                        .setCallbacks(mCallBacks)
                        .build();

                PhoneAuthProvider.verifyPhoneNumber(options);
            }
        });

        register_tv.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            finish();
        });

        mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signIn(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(LoginActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                Toast.makeText(LoginActivity.this, "OTP sent!", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent otpIntent = new Intent(LoginActivity.this, VerificationActivity.class);
                        otpIntent.putExtra("auth", s);
                        startActivity(otpIntent);
                    }
                }, 10000);
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            sendToGroup();
        }
    }

    private void sendToGroup() {
        Intent mainIntent = new Intent(LoginActivity.this, GroupActivity.class);
        startActivity(mainIntent);
    }

    private void signIn(PhoneAuthCredential credential) {

        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    sendToGroup();
                }
                else {
                    Toast.makeText(LoginActivity.this, "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}