package netgloo.models;

import netgloo.beans.SimpleUserBean;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "conversation")
public class Conversation {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    @Id
    private String id;

    @Transient
    public boolean getIsIsnewConversation() {
        return isnewConversation;
    }

    @Transient
    private List<SimpleUserBean> memberList;


    public void setIsnewConversation(boolean isnewConversation) {
        this.isnewConversation = isnewConversation;
    }

    public Conversation() {
        this.isnewConversation = false;
    }

    @Transient
    private boolean isnewConversation;


    private List<String> members;

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    @DBRef
    private List<Message> messageList;

    @Override  // ** don't forget this annotation
    public int hashCode() { // *** note capitalization of the "C"
        return id.hashCode();
    }

    public List<SimpleUserBean> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<SimpleUserBean> memberList) {
        this.memberList = memberList;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        return true;
    }

}
