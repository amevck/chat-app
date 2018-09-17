package netgloo.enums;

public enum MessageType {
    CONVERSASION(1),
    MESSAGE(2),
    USER(3);


    MessageType(int code){
        this.code = code;
    }
    private int code;
}
