package com.example.sot_e.whatsup_clone;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private EditText mPhoneNumber , mCode;
    private Button mSend;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String mVerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        //Check if the user is Logged In
        userIsLoggedIn();

        mPhoneNumber = findViewById(R.id.phoneNumberId);
        mCode = findViewById(R.id.codeId);
        mSend = findViewById(R.id.sendId);

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mVerificationId != null) {
                    VerifyPhoneNumberWithCode();
                } else {
                    startPhoneNumberVerification();
                }

            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                singInWithPhoneAuthCredentials(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(String mVerification, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(mVerificationId, forceResendingToken);

                mVerificationId = mVerification;
                mSend.setText("Verify Code");

            }
        };
    }

    private void VerifyPhoneNumberWithCode() {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId , mCode.getText().toString());
        singInWithPhoneAuthCredentials(credential);
    }

    private void singInWithPhoneAuthCredentials(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                    userIsLoggedIn();
            }
        });
    }

    private void userIsLoggedIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            startActivity(new Intent(getApplicationContext() , MainPageActivity.class));
            finish();
            return;
        }
    }

    private void startPhoneNumberVerification() {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mPhoneNumber.getText().toString(),
                60 ,
                TimeUnit.SECONDS,
                this,
                mCallbacks);

    }
}
