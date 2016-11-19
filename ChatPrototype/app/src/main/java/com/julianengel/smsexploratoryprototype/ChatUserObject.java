package com.julianengel.smsexploratoryprototype;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Thai Flowers on 11/13/2016.
 */

@ParseClassName("_User")
public class ChatUserObject extends ParseUser {
    static final String CHATS_ID_KEY = "chatIds";
    static final String USER_NAME = "userName"; // is email
    static final String USER_PASSWORD  = "userPass";

    public void setUserName(String name) { put(USER_NAME, name); }
    public void setUserPassword(String pass) { put(USER_PASSWORD, pass); }

    public List<ChatUserObject> getChatIds() { return getList(CHATS_ID_KEY); }
    public void addChatIds(List<String> newChatIds) { addAllUnique(CHATS_ID_KEY, newChatIds); }
    public void addChatId(String newChatId) { addUnique(CHATS_ID_KEY, newChatId); }
}
