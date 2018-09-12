package netgloo.repos;


import netgloo.models.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ConversationRepository extends MongoRepository<Conversation, String> {


}