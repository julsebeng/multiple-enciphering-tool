package ChatPrototype.app.src.main.java.com.julianengel.smsexploratoryprototype;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by crow(gridnaught) on 11/24/16.
 */

public class StartupActivity extends AppCompatActivity {

    SharedPreferences p= PreferenceManager.getDefaultSharedPreferences(this);
    boolean firstRun= p.getBoolean(PREFERENCE_FIRST_RUN,true);




    //supposed to take stuff out of apk into phone if first time
    if (firstRun==false)) {

        //right now, it just sets the stream
        AssetManager mngr=getAssets();
        InputStream is2=mngr.open("Ciphers/*");



        mngr.close();

        p.edit().putBoolean(PREFERENCE_FIRST_RUN,false).commit();

    }
}
