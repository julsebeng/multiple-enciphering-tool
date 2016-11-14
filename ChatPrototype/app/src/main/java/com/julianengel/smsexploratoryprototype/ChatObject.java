package com.julianengel.smsexploratoryprototype;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Jengel on 11/10/16.
 */

@ParseClassName("Chat")
public class ChatObject extends ParseObject {

    static final String USER_ID_KEY = "userId";
    static final String CHAT_NAME_KEY = "chatName";

    public String getUserId() {
        return getString(USER_ID_KEY);
    }

    public String getChatName() {
        return getString(CHAT_NAME_KEY);
    }

    public String getChatId() { return getObjectId(); }

    public void setUserId(String userId) {
        put(USER_ID_KEY, userId);
    }

    public void setChatName(String name) { put(CHAT_NAME_KEY, name);
    }

}
