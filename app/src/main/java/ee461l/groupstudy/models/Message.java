package ee461l.groupstudy.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by britne on 11/23/15.
 */
@ParseClassName("Message")
public class Message extends ParseObject {

    private String author;
    private String messageText;

    public Message() { }

    public String getMessageText() {
        return getString("messageText");
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getAuthor() {
        return getString("author");
    }

    public void setUAuthor(String author) {
        this.author = author;
    }

}
