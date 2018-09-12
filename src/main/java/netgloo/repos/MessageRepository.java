package netgloo.repos;
import org.springframework.data.mongodb.repository.MongoRepository;
import netgloo.models.Message;

public interface MessageRepository extends MongoRepository<Message, String> {


}