package com.julianengel.smsexploratoryprototype;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.interceptors.ParseLogInterceptor;

/**
 * Created by jse13 on 10/25/2016.
 */

/* ParseApplication extends Application, which maintains the global application
 * state for this application. Because we're creating a special subclass for
 * our own application, AndroidManifest.xml must be edited with the line:
 * android:name=".ParseApplication"
 * Note: the "." at the beginning of the name indicates that the class being
 * referenced lives inside of the current package (in this case com.julianengel.smsexploratoryprototype)
 */

/* This Application subclass runs before any activity, service, or receiver
 * objects are created, with the exception of content providers.
 */
public class ParseApplication extends Application {

    @Override
    public void onCreate() {

	/* Call Application's onCreate method; this is something the Android 
	 * devs tell you to do (you can see the exact documentation in 
	 * Application.java).
	 */
        super.onCreate();

        /*Register custom parse module in Message.java. The ParseObject is 
	 * the Parse SDK's abstract representaiton of data that is saved and
	 * retrieved from the cloud (the exact specification can be found in
	 * ParseObject.java).
	 */

	/* The registerSubclass function tells ParseObject that we want to use
	 * our own custom structure for the data (defined in Message.java).
	 * Note that we pass in the class literal for Message, because of 
	 * registerSubclass's arguments:
	 * public static void registerSubclass(Class<? extends ParseObject> subclass)
	 */
        ParseObject.registerSubclass(Message.class);

        /* Define our own server information for parse. initialize() takes in
	 * a Configuration as a parameter. The Configuration class is defined
	 * in Parse.java. 
	 *
	 * Here's the following breakdown of what this statement accomplishes:
	 *
	 * -Builder(this): passes our current context into the Configuration object.
	 * -applicationId(): sets object's applicationId. Returns the Builder.
	 * -addNetworkInterceptor(): registers a new ParseLogInterceptor interface.
	 * -server(): server URL to be used by Parse; important because we are 
	 * 	using our own server and not Parse's own servers.
	 * -build(): this function will return a newly created Configuration 
	 *  	object, which is created using the Configuration(Builder) 
	 *  	constructor. The Builder used is the one initially created and
	 *  	returned by applicationId().
	 */
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("Labyrinthine")
                .addNetworkInterceptor(new ParseLogInterceptor())
                .server("https://labyrinthine.herokuapp.com/parse/").build());

    }
}
