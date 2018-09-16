package netgloo.services;

import netgloo.beans.SimpleUserBean;
import netgloo.models.Conversation;
import netgloo.models.User;
import netgloo.repos.ConversationRepository;
import netgloo.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for sending notification messages.
 */
@Service
public class UserService {
  
  // The SimpMessagingTemplate is used to send Stomp over WebSocket messages.
  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  @Autowired
  private UserRepository userRepository;
  
  /**
   * Send notification to users subscribed on channel "/user/queue/notify".
   *
   * The message will be sent only to the user with the given username.
   *
   */
  public User getUserByEmail( String email) {
    try {
      User user = userRepository.findByEmail(email);
      if(user.getConversation() != null) {
        user.getConversation().forEach(
                conversation -> {
                  List<SimpleUserBean> memberList = new ArrayList<>();
                  conversation.getMembers().forEach(
                          member -> {
                            Optional<User> optionalUser = userRepository.findById(member);
                            if(optionalUser.isPresent()) {
                              User userInMembers = optionalUser.get();
                              memberList.add(new SimpleUserBean(userInMembers.getId(), userInMembers.getUserName(), userInMembers.getEmail(), userInMembers.getFirstName(), userInMembers.getLastName()));
                            }
                            }
                  );
                  conversation.setMemberList(memberList);
                }
        );
      }
      return user;
    }catch(Exception e){
      e.printStackTrace();
    }
    return null;
  }



} // class NotificationService
