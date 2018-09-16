package netgloo.beans;

import java.util.Set;

public class UserBean {
    public UserBean() {
    }

    private String id;
    private String email;
    private String fullname;
    private String userName;

    public UserBean(String id, String email, String fullname, String userName, Set<ConversationBean> conversation) {
        this.id = id;
        this.email = email;
        this.fullname = fullname;
        this.userName = userName;
        this.conversation = conversation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<ConversationBean> getConversation() {
        return conversation;
    }

    public void setConversation(Set<ConversationBean> conversation) {
        this.conversation = conversation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private Set<ConversationBean> conversation;

}
