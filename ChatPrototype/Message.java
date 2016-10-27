package com.julianengel.smsexploratoryprototype;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Julian and Chris on 10/25/16.
 */
@ParseClassName("Message")
public class Message extends ParseObject {

    static final String USER_ID_KEY = "userId";
    static final String BODY_KEY = "body";

    public String getUserId() {
        return getString(USER_ID_KEY);
    }

    public String getBody() {
        return getString(BODY_KEY);
    }

    public void setUserId(String userId) {
        put(USER_ID_KEY, userId);
    }

    public void setBody(String body) {
        put(BODY_KEY, body);
    }

}
