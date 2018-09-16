package netgloo.beans;


import netgloo.enums.MessageType;

public class GenericMessage<T> {

    private T obj;

    private MessageType type;

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public GenericMessage() {
    }

    public MessageType getType() {
        return type;
    }

    public GenericMessage(T obj, MessageType type) {
        this.obj = obj;
        this.type = type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
