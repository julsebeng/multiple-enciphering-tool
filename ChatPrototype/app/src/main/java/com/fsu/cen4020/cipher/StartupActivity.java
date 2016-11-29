package com.fsu.cen4020.cipher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;

/**
 * Created by crow(gridnaught) ((Joseph)) on 11/24/16.
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

            try {
                AssetManager mngr = getAssets();
                // get list of files in the cipher-library subdirectory of assets
                String[] cyphFiles = mngr.list("cipher-library");
                // destination path, something like /data/data/<package_name>/files/
                String destPath =
                        context.getFilesDir().getPath()
                        + File.separator
                        + "cipher-library";
                // Make File out of string, and make sure the File represents a directory
                File destDir = new File(destPath);
                if (!destDir.exists())
                    destDir.mkdirs();
                for (int i = 0; i < cyphFiles.length; i++) {
                    String destFile =
                        destPath
                        + File.separator
                        + cyphFiles[i];
                    if ( (new File(destFile).exists()) )
                        Log.i(TAG, destFile + " Exists");
                    else {
                        Log.i(TAG, "Creating " + destFile);

                        OutputStream OS = null;
                        InputStream IS = null;
                        try {
                            // Use asset manager to fetch a file-descriptor for the new file
                            // and use that to open an InputStream (needed by most copy methods)
                            IS = mngr.openFd(
                                    "cipher-library"
                                    + File.separator
                                    + cyphFiles[i])
                                .createInputStream();
                            if (IS == null) {
                                throw new IOException("IS is null");
                            }
                            OS = new FileOutputStream(new File(destFile));
                            if (OS == null) {
                                throw new IOException("OS is null");
                            }

                            // Apparently only the crappy primitive method works here
                            byte[] buf = new byte[1024];
                            int len;

                            while ((len = IS.read(buf)) > 0) {
                                OS.write(buf, 0, len);
                            }

                            IS.close();
                            OS.close();
                        }
                        catch (IOException ex) {
                            ex.printStackTrace();
                            throw ex;
                        }
                        finally {
                            if (OS != null)
                                OS.close();
                            if (IS != null)
                                IS.close();
                        }
                    }
                }
            }
            catch(IOException ex){
                Log.e(TAG, "Failed to install files properly");
                Log.e(TAG, ex.getMessage());
                finish();
            }

            prefs.edit().putBoolean("FIRST_RUN", false).commit();
        //}

        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}
