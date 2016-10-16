package cen4020.multiple_enciphering_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button encryptBtn;
    Button decryptBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        encryptBtn = (Button) findViewById(R.id.encryptBtn);
        decryptBtn = (Button) findViewById(R.id.decryptBtn);

        encryptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), EncryptActivity.class);
                startActivity(myIntent);

            }
        });

        decryptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), DecryptActivity.class);
                startActivity(myIntent);
            }
        });


    }
}
