package ChatPrototype.app.src.main.java.com.julianengel.smsexploratoryprototype;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.content.res.AssetManager;

import com.julianengel.smsexploratoryprototype.LoginActivity;

import java.io.InputStream;

/**
 * Created by crow(gridnaught) on 11/24/16.
 */

public class StartupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);

        //supposed to take stuff out of apk into phone if first time
        if (p.getBoolean("FIRST_RUN", true)) {

            //right now, it just sets the stream
            AssetManager mngr = getAssets();
            //InputStream is2=mngr.open("Ciphers/*");

            mngr.close();

            p.edit().putBoolean("FIRST_RUN", false).commit();

        }

        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}
