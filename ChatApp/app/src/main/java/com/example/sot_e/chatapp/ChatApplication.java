package com.example.sot_e.chatapp;

import android.app.Application;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class ChatApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this);

        ParseObject.registerSubclass(Model.Message.class);


    }
}
