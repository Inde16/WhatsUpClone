package com.example.sot_e.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ParseInstallation;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private Button loginButton;
    private Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button) findViewById(R.id.LoginID);
        createAccountButton = (Button) findViewById(R.id.CreateAccID);


        createAccountButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.LoginID:
                //we do somthing
                break;

            case R.id.CreateAccID:
                startActivity(new Intent(MainActivity.this , CreateAccount.class));
                break;

        }

    }
}
