package cen4020.multiple_enciphering_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EncryptActivity extends AppCompatActivity {

    Button cipherBtn;
    TextView msgEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);

        cipherBtn = (Button) findViewById(R.id.cipherBtn);
        msgEditText = (TextView) findViewById(R.id.msgEditText);

        cipherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msgText = msgEditText.getText().toString();
            }
        });

    }


}
