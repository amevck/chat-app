package netgloo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "conversation")
public class Conversation {

    @Id
    private String id;

    @DBRef(lazy = true)
    private User members;

    @DBRef
    private List<Message> messageList;

}
