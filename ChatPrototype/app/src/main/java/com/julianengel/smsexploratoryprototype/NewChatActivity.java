package com.julianengel.smsexploratoryprototype;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class NewChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Button submit = (Button) findViewById(R.id.NewChatSubmit);
        final EditText etName = (EditText) findViewById(R.id.etChatName);

        submit.setEnabled(false);
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etName.getText().toString().equals(""))
                    submit.setEnabled(false);
                else
                    submit.setEnabled(true);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String input = etName.getText().toString();

                final ParseObject newChat = new ChatObject();
                newChat.put("chatName", input);
                newChat.put("userId", ParseUser.getCurrentUser().getObjectId());
                newChat.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            String chatId = newChat.getObjectId();

                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("chatId", chatId);
                            setResult(Activity.RESULT_OK, resultIntent);

                            finish();
                        }
                    }
                });
             }
        });
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
