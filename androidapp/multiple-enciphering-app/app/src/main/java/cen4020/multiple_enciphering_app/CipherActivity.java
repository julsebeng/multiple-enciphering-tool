package cen4020.multiple_enciphering_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CipherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cipher);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String msgText = extras.getString("msgText");

        }
    }
}
