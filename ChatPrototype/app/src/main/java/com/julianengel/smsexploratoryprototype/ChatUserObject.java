package com.julianengel.smsexploratoryprototype;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Thai Flowers on 11/13/2016.
 */

@ParseClassName("_ChatUser")
public class ChatUserObject extends ParseUser {
    static final String CHATS_ID_KEY = "chatIds";

    public List<ChatUserObject> getChatIds() { return getList(CHATS_ID_KEY); }
    public void addChatIds(List<ParseObject> newChatIds) { addAllUnique(CHATS_ID_KEY, newChatIds); }
    public void addChatId(ParseObject newChatId) { addUnique(CHATS_ID_KEY, newChatId); }
}
