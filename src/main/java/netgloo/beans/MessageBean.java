package netgloo.beans;

import netgloo.models.Message;

import java.util.Date;

public class MessageBean {

    private String id;

    public MessageBean() {

    }

    public MessageBean(String id, String message, Date date, SimpleUserBean sender, SimpleUserBean receiver) {
        this.id = id;
        this.message = message;
        this.date = date;
        this.sender = sender;
        this.receiver = receiver;
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

    public SimpleUserBean getSender() {
        return sender;
    }

    public void setSender(SimpleUserBean sender) {
        this.sender = sender;
    }

    public SimpleUserBean getReceiver() {
        return receiver;
    }

    public void setReceiver(SimpleUserBean receiver) {
        this.receiver = receiver;
    }

    private String message;

    private Date date;


    private SimpleUserBean sender;


    private SimpleUserBean receiver;

}
