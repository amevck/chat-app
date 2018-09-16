package netgloo.services;

import netgloo.Notification;
import netgloo.beans.GenericMessage;
import netgloo.beans.SimpleUserBean;
import netgloo.enums.MessageType;
import netgloo.models.Conversation;
import netgloo.models.Message;
import netgloo.models.User;
import netgloo.repos.ConversationRepository;
import netgloo.repos.MessageRepository;
import netgloo.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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

  @Autowired
  private NotificationService notificationService;



  @Autowired
  private ConversationRepository conversationRepository;
  
  /**
   * Send notification to users subscribed on channel "/user/queue/notify".
   *
   * The message will be sent only to the user with the given username.
   *
   */
  public void SendMessage(Principal principal,Notification notification){
    Message message = null;

    try {

       message = saveMessage(principal,notification);
      if(message == null){
        System.out.println("not saved");
      }

      String conversationId = notification.getConversationId();
      Conversation conversation;

      User sender = this.userRepository.findByEmail(notification.getSender());
      User reciever = this.userRepository.findByEmail(notification.getReciever());

      if(conversationId == null ) {

          conversation = new Conversation();
        List<String> memberList = new ArrayList<>();
        memberList.add(sender.getId());
        memberList.add(reciever.getId());
          conversation.setMembers(memberList);

        }else{
        Optional<Conversation> conversationOptional = conversationRepository.findById(conversationId);
        if(conversationOptional.isPresent()) {
          conversation = conversationOptional.get();
          conversation.setIsnewConversation(false);
        }else {
          List<String> memberList = new ArrayList<>();
          conversation = new Conversation();
          memberList.add(sender.getId());
          memberList.add(reciever.getId());
          conversation.setMembers(memberList);
        }
      }

          if (conversation.getMessageList() == null) {
            conversation.setMessageList(new ArrayList<>());
          }
          conversation.getMessageList().add(message);
          Conversation savedConversation = conversationRepository.save(conversation);

      List<SimpleUserBean> memberList = new ArrayList<>();
      if(savedConversation.getMemberList() == null){
        savedConversation.setMemberList(new ArrayList<>());
      }
      User userInMembers;
      userInMembers = reciever;

      userInMembers = reciever;
      savedConversation.getMemberList().add(new SimpleUserBean(userInMembers.getId(), userInMembers.getUserName(), userInMembers.getEmail(), userInMembers.getFirstName(), userInMembers.getLastName()));
      userInMembers = sender;
      savedConversation.getMemberList().add(new SimpleUserBean(userInMembers.getId(), userInMembers.getUserName(), userInMembers.getEmail(), userInMembers.getFirstName(), userInMembers.getLastName()));


          if (savedConversation != null) {
            message.setConversationId(conversation.getId());
            if(sender.getConversation() == null){
              sender.setConversation(new HashSet<Conversation>());
            }
            if(reciever.getConversation() == null){
              reciever.setConversation(new HashSet<Conversation>());
            }
            sender.getConversation().add(savedConversation);
            reciever.getConversation().add(savedConversation);

            userRepository.save(sender);
            userRepository.save(reciever);
            if(conversationId != null) {
              notificationService.notify(
                      new GenericMessage<Message>(message, MessageType.MESSAGE), // notification object
                      notification.getReciever()                    // username
              );
              notificationService.notify(
                      new GenericMessage<Message>(message, MessageType.MESSAGE), // notification object
                      notification.getSender()                     // username
              );
            }
            if(conversationId == null){
              savedConversation.setIsnewConversation(true);
              notificationService.notify(
                      new GenericMessage<Conversation>(savedConversation, MessageType.CONVERSASION) , // notification object
                      notification.getReciever()                    // username
              );
              notificationService.notify(
                      new GenericMessage<Conversation>(savedConversation, MessageType.CONVERSASION), // notification object
                      notification.getSender()                     // username
              );
            }
          }

      }catch (Exception e){
        e.printStackTrace();
      }




  }

  public Message SaveMessage( Principal principal,Message message) {
    return messageRepository.save(message);
  }

  public Message saveMessage( Principal principal,Notification notification) {
    User reciever = this.userRepository.findByEmail(notification.getReciever());
    User sender = this.userRepository.findByEmail(notification.getSender());

    if(reciever!=null && sender!=null){

        Message message = new Message(notification.getMessage(),sender.getId(),sender.getEmail() );


      return this.messageRepository.save(message);
    }
    return  null;
  }
  
} // class NotificationService
