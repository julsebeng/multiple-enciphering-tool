package com.julianengel.smsexploratoryprototype;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.julianengel.smsexploratoryprototype.R.id.etMessage;

public class ChatActivity extends AppCompatActivity {
    static final String TAG = ChatActivity.class.getSimpleName();

    //Used in setupMessagePosting()
    //These are keys for the backend DB
    static final String USER_ID_KEY = "userId";
    static final String BODY_KEY = "body";
    //These hold the send button and message GUI elements
    EditText etMessage;
    Button btSend;
    ListView lvChat;
    ArrayList<Message> mMessages;
    ChatListAdapter mAdapter;

    boolean mFirstLoad;

    static final int MAX_MESSAGES_TO_SHOW = 50;

    static final int POLL_INTERVAL = 1000;
    Handler mHandler = new Handler();
    Runnable mRefreshMessagesRunnable = new Runnable() {
        @Override
        public void run() {
            refreshMessages();
            mHandler.postDelayed(this, POLL_INTERVAL);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        if(ParseUser.getCurrentUser() != null) {
            startWithCurrentUser();
        }
        else {
            login();
        }

        mHandler.postDelayed(mRefreshMessagesRunnable, POLL_INTERVAL);



    }

    //Get cached user information
    void startWithCurrentUser() {
        setupMessagePosting();
    }

    void login() {
        ParseAnonymousUtils.logIn(new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null) {
                    Log.e(TAG,"Anonymous login failed: ", e);
                }
                else {
                    startWithCurrentUser();
                }
            }
        });
    }

    //Sets up the button event handler that will post the message to the parse server
    void setupMessagePosting() {

        //Get the EditText and the button
        etMessage = (EditText) findViewById(R.id.etMessage);
        btSend = (Button) findViewById(R.id.btSend);
        lvChat = (ListView)findViewById(R.id.lvChat);
        mMessages = new ArrayList<>();

        //Scroll to the bottom when a data set change occurs
        lvChat.setTranscriptMode(1);
        mFirstLoad = true;
        final String userId = ParseUser.getCurrentUser().getObjectId();
        mAdapter = new ChatListAdapter(ChatActivity.this, userId, mMessages);
        lvChat.setAdapter(mAdapter);


        //Create message when button is pressed and save it to the remote Parse DB
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                String data = etMessage.getText().toString();

                //Make a new instance of our extended ParseObject called Message
                Message message = new Message();
                //Fill in data from EditText
                message.setBody(data);
                //Set the user
                message.setUserId(ParseUser.getCurrentUser().getObjectId());

                message.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null) {
                            Toast.makeText(ChatActivity.this, "Successfully sent message to DB", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Log.e(TAG, "Failed to send message", e);
                        }
                    }
                });
                etMessage.setText(null);
            }
        });


    }

    //Get messages from Parse and load them into the cat adapter
    void refreshMessages() {
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);

        //Limit and sort results
        query.setLimit(MAX_MESSAGES_TO_SHOW);
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<Message>() {
           public void done(List<Message> messages, ParseException e) {
               if(e == null) {
                   mMessages.clear();
                   Collections.reverse(messages);
                   mMessages.addAll(messages);
                   mAdapter.notifyDataSetChanged();

                   if(mFirstLoad) {
                       lvChat.setSelection(mAdapter.getCount() - 1);
                       mFirstLoad = false;
                   }
               }
               else {
                   Log.e("Message", "Error Loading Messages" + e);
               }
           }
        });
    }


}
