package netgloo.services;

import netgloo.Notification;

import netgloo.beans.GenericMessage;
import netgloo.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Service class for sending notification messages.
 */
@Service
public class NotificationService {
  
  // The SimpMessagingTemplate is used to send Stomp over WebSocket messages.
  @Autowired
  private SimpMessagingTemplate messagingTemplate;
  
  /**
   * Send notification to users subscribed on channel "/user/queue/notify".
   *
   * The message will be sent only to the user with the given username.
   * 
   * @param genericMessage The notification message.
   * @param username The username for the user to send notification.
   */
  public void notify(GenericMessage genericMessage, String username) {

    messagingTemplate.convertAndSendToUser(
      username, 
      "/queue/notify",
            genericMessage
    );
    return;
  }
  
} // class NotificationService
