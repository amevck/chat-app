package netgloo;

import netgloo.models.Conversation;

import javax.security.auth.login.Configuration;

public class Notification {

  private String message;
  public String reciever;
  public String sender;
  public String conversationId;

  public String getConversationId() {
    return conversationId;
  }

  public void setConversationId(String conversationId) {
    this.conversationId = conversationId;
  }

  public String getMessage() {
    return message;
  }

  public String getReciever() {
    return reciever;
  }
  public Notification() {

  }

  public Notification(String message) {
    this.message = message;
  }
  public Notification(String message,String sender,String reciever,String conversationId) {
    this.message = message;
    this.reciever = reciever;
    this.sender = sender;
    this.conversationId = conversationId;
  }

  public void setReciever(String reciever) {
    this.reciever = reciever;
  }

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
