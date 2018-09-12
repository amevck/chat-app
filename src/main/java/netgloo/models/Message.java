package netgloo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "message")
public class Message {

    @Id
    private String id;

    private String message;

    private Date date;

    @DBRef(lazy = true)
    private User sender;

    @DBRef(lazy = true)
    private User receiver;


    public  Message(){
        this.date = new Date();
    }

    public Message(User reciever,User Sender,String message){
        this.date = new Date();
        this.receiver = reciever;
        this.sender = Sender;
        this.message = message;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}
