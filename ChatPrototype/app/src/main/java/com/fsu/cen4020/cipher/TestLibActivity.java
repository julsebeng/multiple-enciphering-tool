package com.fsu.cen4020.cipher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import cipher.*;

public class TestLibActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_lib);

        Cipher cipher = new DummyCipher();
    }

}
