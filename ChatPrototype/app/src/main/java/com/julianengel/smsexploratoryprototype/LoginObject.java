package com.julianengel.smsexploratoryprototype;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by Julian on 11/3/16.
 */

@ParseClassName("LoginCreds")
public class LoginObject extends ParseObject {

    static final String LOGIN_EMAIL = "login_email";
    static final String LOGIN_PASS  = "login_pass";

    public void setLoginEmail(String email) {
        put(email, LOGIN_EMAIL);
    }

    public void setLoginPass(String pass) {
        put(pass, LOGIN_PASS);
    }
}
