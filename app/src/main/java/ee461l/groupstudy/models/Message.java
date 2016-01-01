package ee461l.groupstudy.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by britne on 11/23/15.
 */
@ParseClassName("Message")
public class Message extends ParseObject {

    private String userId;
    private String messageText;

    public Message() { }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
