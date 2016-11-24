package com.julianengel.smsexploratoryprototype;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by crow(gridnaught) on 11/24/16.
 */

public class StartupActivity extends AppCompatActivity {
    static final String TAG = StartupActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Context context = getApplicationContext();

        //supposed to take stuff out of apk into phone if first time
        //if (prefs.getBoolean("FIRST_RUN", true)) {

            AssetManager mngr = getAssets();

            try {
                String[] cyphFiles = mngr.list("cipher-library");
                for (int i = 0; i < cyphFiles.length; i++) {
                    String destPath =
                            context.getFilesDir().getPath()
                            + File.separator
                            + cyphFiles[i];
                    if (!new File(destPath).exists()) {
                        FileOutputStream OS = null;
                        FileInputStream IS = null;
                        try {
                            IS = mngr.openFd(
                                "cipher-library"
                                + File.separator
                                + cyphFiles[i])
                                .createInputStream();
                            OS = new FileOutputStream(destPath);

                            FileChannel inChan = IS.getChannel();
                            FileChannel ouChan = OS.getChannel();

                            long position = 0;
                            long size = inChan.size();
                            // transferTo may fail for files > 1 MB in size (like book files)
                            // use loop to transfer by chunks
                            while (position < size) {
                                long count = inChan.transferTo(position, size, ouChan);
                                if (count > 0) {
                                    position += count;
                                    size -= count;
                                }
                            }

                            OS.flush();
                        }
                        catch (IOException ex) {
                            throw ex;
                        }
                        finally {
                            OS.close();
                            IS.close();
                        }
                    }
                }
            }
            catch(IOException ex){
                Log.e(TAG, "Failed to install files properly");
            }

            prefs.edit().putBoolean("FIRST_RUN", false).commit();
        //}

        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}
