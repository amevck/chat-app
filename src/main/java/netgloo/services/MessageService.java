package netgloo.services;

import netgloo.Notification;
import netgloo.models.Message;
import netgloo.models.User;
import netgloo.repos.MessageRepository;
import netgloo.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Service class for sending notification messages.
 */
@Service
public class MessageService {
  
  // The SimpMessagingTemplate is used to send Stomp over WebSocket messages.
  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  @Autowired
  private MessageRepository messageRepository;

  @Autowired
  private UserRepository userRepository;
  
  /**
   * Send notification to users subscribed on channel "/user/queue/notify".
   *
   * The message will be sent only to the user with the given username.
   *
   */
  public Message SaveMessage( Message message) {
    return messageRepository.save(message);
  }

  public Message SaveMessage( Notification notification) {
    User reciever = this.userRepository.findByEmail(notification.getReciever());
    User sender = this.userRepository.findByEmail(notification.getSender());

    if(reciever!=null && sender!=null){
      Message message = new Message(reciever,sender,notification.getMessage());
      return this.messageRepository.save(message);
    }
    return  null;
  }
  
} // class NotificationService
