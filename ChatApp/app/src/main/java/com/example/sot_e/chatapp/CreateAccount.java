package com.example.sot_e.chatapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import Util.ProgressGenerator;

public class CreateAccount extends AppCompatActivity implements ProgressGenerator.OnCompleteListener {

    private EditText emailAddress , username , password;
    private ProgressGenerator progressGenerator;
    private ActionProcessButton createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //Set things up
        progressGenerator = new ProgressGenerator(this);
        createAccountButton = (ActionProcessButton) findViewById(R.id.CreateID);
        emailAddress = (EditText) findViewById(R.id.UserEmailID);
        username = (EditText) findViewById(R.id.UsernameAccountID);
        password = (EditText) findViewById(R.id.UserPasswordID);


        createAccountButton.setMode(ActionProcessButton.Mode.PROGRESS);


        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
    }


    private void CreateAccount(){

        final String uEmail = emailAddress.getText().toString();
        final String uName = username.getText().toString();
        final String uPass = password.getText().toString();


        if (uEmail.equals("") || uName.equals("") || uPass.equals("")){

            final AlertDialog.Builder dialog = new AlertDialog.Builder(CreateAccount.this);

            dialog.setTitle("Empty Fields");

            dialog.setMessage("Please Complete the Form");

            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.dismiss();

                }
            });
            dialog.show();

        }else{

            ParseUser user = new ParseUser();

            //Set some Core Properties
            user.setUsername(uName);
            user.setPassword(uPass);
            user.setEmail(uEmail);


            //Set a custom Property
            user.put("city" , "Spokane");


            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {

                    if( e == null){
                        progressGenerator.start(createAccountButton);
                        createAccountButton.setEnabled(false);

                        emailAddress.setEnabled(false);
                        username.setEnabled(false);
                        password.setEnabled(false);

                        //Log them in
                        LogUserIn(uName , uPass);
                    }


                }
            });
        }


    }

    private void LogUserIn(String uName, String uPass) {

        if(!uName.equals("") || !uPass.equals("")){

            ParseUser.logInInBackground(uName, uPass, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {

                    if (e == null){

                        Log.v("USER LOGGED IN ID:" , user.getUsername());

                    }else{


                    }

                }
            });

        }else{

        }

    }

    @Override
    public void OnComplete() {
        startActivity(new Intent(CreateAccount.this , ChatActivity.class));
    }
}
