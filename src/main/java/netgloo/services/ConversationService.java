package netgloo.services;

import netgloo.models.Conversation;
import netgloo.models.Message;
import netgloo.repos.ConversationRepository;
import netgloo.repos.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.security.auth.login.Configuration;

/**
 * Service class for sending notification messages.
 */
@Service
public class ConversationService {
  
  // The SimpMessagingTemplate is used to send Stomp over WebSocket messages.
  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  @Autowired
  private ConversationRepository conversationRepository;
  
  /**
   * Send notification to users subscribed on channel "/user/queue/notify".
   *
   * The message will be sent only to the user with the given username.
   *
   */
  public Conversation SaveConversation( Conversation conversation) {
    return conversationRepository.save(conversation);
  }



} // class NotificationService
