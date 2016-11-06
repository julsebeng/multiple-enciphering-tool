package com.julianengel.smsexploratoryprototype;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Julian and Chris on 10/25/16.
 */
/* This subclass defines our own structure for the data that Parse sends and
 * receives from the server; used in ParseApplication.java, when it's passed
 * to registerSubclass as a class literal.
 */
@ParseClassName("Message")
public class Message extends ParseObject {

	/* Constant values defined for field names; these are what will appear as
	 * the keys in the backend DB
	 */
    static final String USER_ID_KEY = "userId";
    static final String BODY_KEY = "body";

	/* Getter functions; even though these just call ParseObject functions, they
	 * are more friendly and easier to use.
	 */
    public String getUserId() {
        return getString(USER_ID_KEY);
    }

    public String getBody() {
        return getString(BODY_KEY);
    }

	/* Setter functions: same with the getters, these are just easy-to-use
	 * interfaces
	 */
    public void setUserId(String userId) {
        put(USER_ID_KEY, userId);
    }

    public void setBody(String body) {
        put(BODY_KEY, body);
    }

}
