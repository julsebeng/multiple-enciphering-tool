package com.julianengel.smsexploratoryprototype;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.interceptors.ParseLogInterceptor;

/**
 * Created by jse13 on 10/25/2016.
 */

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Register custom parse module in Message.java
        ParseObject.registerSubclass(Message.class);

        //Define our own server information for parse
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("Labyrinthine")
                .clientKey(null)
                .addNetworkInterceptor(new ParseLogInterceptor())
                .server("https://labyrinthine.herokuapp.com/parse/").build());

    }
}
