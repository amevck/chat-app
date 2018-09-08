package netgloo;

public class Notification {

  private String message;
  public String reciever;
  public String sender;

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
  public Notification(String message,String sender,String reciever) {
    this.message = message;
    this.reciever = reciever;
    this.sender = sender;
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
