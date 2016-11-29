package com.example.kclem_000.multiple_enciphering_sample;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends Activity {

    EditText ed;
    Button btn;
    TextView result;
    String h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed = (EditText) findViewById(R.id.contexttxt);
        result = (TextView) findViewById(R.id.resulttxt);
        btn = (Button) findViewById(R.id.exportbtn);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try{
                    h = DateFormat.format("MM-dd-yyyyy-h-mmssaa", System.currentTimeMillis()).toString();
                    // this will create a new name everytime
                    File root = new File(Environment.getExternalStorageDirectory(), "Notes");
                    File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                    // if external memory exists and folder with name Notes
                    if(!root.exists()){
                        root.mkdirs(); // will create folder
                    }
                    File filepath = new File(dir, h + ".txt"); // file path to save
                    FileWriter writer = new FileWriter(filepath);
                    writer.append(ed.getText().toString());
                    writer.flush();
                    writer.close();
                    String m = "File generated with name " + h + ".txt";
                    result.setText(m);
                } catch(IOException e){
                    e.printStackTrace();
                    result.setText(e.getMessage().toString());
                }
            }
        });
    }
}
