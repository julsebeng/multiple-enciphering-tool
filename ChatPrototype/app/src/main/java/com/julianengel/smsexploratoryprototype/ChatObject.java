package com.julianengel.smsexploratoryprototype;

import com.parse.ParseObject;

/**
 * Created by Jengel on 11/10/16.
 */

public class ChatObject extends ParseObject {

    static final String USER_ID_KEY = "userId";
    static final String CHAT_ID_KEY = "chatId";

    public String getUserId() {
        return getString(USER_ID_KEY);
    }

    public String getChatBody() {
        return getString(CHAT_ID_KEY);
    }

    public void setUserId(String userId) {
        put(USER_ID_KEY, userId);
    }

    public void setChatId(String body) {
        put(CHAT_ID_KEY, body);
    }

}
