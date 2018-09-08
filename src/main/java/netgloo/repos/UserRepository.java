package netgloo.repos;


import netgloo.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;;

public interface UserRepository extends MongoRepository<User, String> {

    public User findByUserName(String name);

    public User findByEmail(String name);


}