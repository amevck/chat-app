package netgloo.beans;

import java.util.List;

public class ConversationBean {


    private String id;

    public ConversationBean() {
    }

    public ConversationBean(String id, List<SimpleUserBean> members, List<MessageBean> messageList) {
        this.id = id;
        this.members = members;
        this.messageList = messageList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<SimpleUserBean> getMembers() {
        return members;
    }

    public void setMembers(List<SimpleUserBean> members) {
        this.members = members;
    }

    public List<MessageBean> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<MessageBean> messageList) {
        this.messageList = messageList;
    }

    private List<SimpleUserBean> members;


    private List<MessageBean> messageList;
}
