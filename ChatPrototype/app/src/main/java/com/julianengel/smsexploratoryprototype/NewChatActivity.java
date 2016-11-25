package com.julianengel.smsexploratoryprototype;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cipher.CipherSequenceLibrary;

public class NewChatActivity extends AppCompatActivity {

    static final String TAG = ChatActivity.class.getSimpleName();

    Button submit;
    EditText etName;
    EditText etEmail;
    Spinner spCipher;

    List<String> cyphFiles;
    CipherSequenceLibrary library;

    boolean bName = false;
    boolean bEmail = false;
    boolean bCipher = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Context context = getApplicationContext();
        try {
            library = new CipherSequenceLibrary (
                    context.getFilesDir().getPath() + File.separator + "cipher-library");
            cyphFiles = library.fileNames();
        }
        catch (IOException ex) {
            Log.e(TAG, "A file error occurred in NewChatActivity");
            finish();
        }
        catch (Exception ex) {
            Log.e(TAG, "A CipherSequenceLibrary error occurred in NewChatActivity");
            finish();
        }

        submit = (Button) findViewById(R.id.NewChatSubmit);
        etName = (EditText) findViewById(R.id.etChatName);
        etEmail = (EditText) findViewById(R.id.etChatEmail);
        spCipher = (Spinner) findViewById(R.id.spChatCipher);

        submit.setEnabled(false);

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etName.getText().toString().equals(""))
                    bName = false;
                else
                    bName = true;
                checkFormHelper();
            }
        });

        etEmail.addTextChangedListener( new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etEmail.getText().toString().equals(""))
                    bEmail = false;
                else
                    bEmail = true;
                checkFormHelper();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cyphFiles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCipher.setAdapter(adapter);

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
    private void checkFormHelper() {
        if (bName && bEmail && bCipher)
            submit.setEnabled(true);
        else
            submit.setEnabled(false);

    }

}
