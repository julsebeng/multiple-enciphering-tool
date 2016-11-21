package com.julianengel.smsexploratoryprototype;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import cipher.*;

public class TestLibActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_lib);

        Cipher cipher = new Cipher() {
            @Override
            public Object clone() throws CloneNotSupportedException {
                return null;
            }

            @Override
            public boolean equals(Cipher cipher) {
                return false;
            }

            @Override
            public String encrypt(String s) throws Exception {
                return null;
            }

            @Override
            public String decrypt(String s) throws Exception {
                return null;
            }
        };
    }

}
