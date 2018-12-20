package com.example.sot_e.chatapp;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.RenderProcessGoneDetail;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.dd.processbutton.ProcessButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import Data.ChatAdaptor;
import Util.ProgressGenerator;



public class ChatActivity extends AppCompatActivity {

    private EditText message;
    private Button sendButton;
    private ProgressGenerator progressGenerator;
    public static final String USER_KEY_ID = "userId";
    private String currentUserId;
    private ListView listView;
    private ArrayList<Model.Message> mMessages;
    private ChatAdaptor mAdapter;
    private Handler handler = new Handler();
    private  static final int MAX_CHAT_MSG_TO_SHOW = 70;



    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            RefreshMessages();
            handler.postDelayed(this, 100);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getCurrentUser();

        //Stop at 3:29 LESSON 233

        handler.postDelayed(runnable, 100);
    }


    public void getCurrentUser() {
        currentUserId = ParseUser.getCurrentUser().getObjectId();
        messagePosting();
    }

    private void messagePosting() {

       message = (EditText) findViewById(R.id.etMessage);
       sendButton = (Button) findViewById(R.id.buttonSend);
       listView = (ListView) findViewById(R.id.listViewChat);
       mMessages = new ArrayList<Model.Message>();
       mAdapter = new ChatAdaptor(ChatActivity.this , currentUserId , mMessages);
       listView.setAdapter(mAdapter);

       sendButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               if(!message.getText().equals("")){

                   Model.Message msg = new Model.Message();
                   msg.setUserId(currentUserId);
                   msg.setBody(message.getText().toString());
                   msg.saveInBackground(new SaveCallback() {
                       @Override
                       public void done(ParseException e) {
                            receiveMessage();
                       }
                   });

               }else{
                   Toast.makeText(getApplicationContext(), "Empty message" , Toast.LENGTH_LONG).show();
               }

           }
       });



    }

    private void receiveMessage() {

        ParseQuery<Model.Message> query = ParseQuery.getQuery(Model.Message.class);

        query.setLimit(MAX_CHAT_MSG_TO_SHOW);
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<Model.Message>() {
            @Override
            public void done(List<Model.Message> messages, ParseException e) {

                if ( e == null){

                    mMessages.clear();
                    mMessages.addAll(messages);
                    mAdapter.notifyDataSetChanged();
                    listView.invalidate();
                }else{

                    Log.v("Error:" , "Error: " + e.getMessage());
                }

            }
        });


    }

    private void RefreshMessages(){

        receiveMessage();

    }
}
