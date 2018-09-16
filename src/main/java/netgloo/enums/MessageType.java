package netgloo.enums;

public enum MessageType {
    CONVERSASION(1),
    MESSAGE(2);


    MessageType(int code){
        this.code = code;
    }
    private int code;
}
