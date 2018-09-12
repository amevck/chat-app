package netgloo.controllers;

import netgloo.Notification;
import netgloo.models.Message;
import netgloo.models.User;
import netgloo.repos.MessageRepository;
import netgloo.services.MessageService;
import netgloo.services.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.Response;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Controller
public class MainController {

  @Autowired
  private NotificationService notificationService;

  @Autowired
  private SessionRegistry sessionRegistry;



  @Autowired
  private MessageService messageService;

  /**
   * GET  /  -> show the index page.
   */
//  @RequestMapping("/")
//  public String index() {
//    return "index";
//  }

  /**
   * GET  /notifications  -> show the notifications page.
   */
  @MessageMapping("/send/message")
  public String notifications(Notification notification) {


Message message = messageService.SaveMessage(notification);
if(message == null){
  System.out.println("not saved");
}
    notificationService.notify(
            new Notification(notification.getMessage(),notification.getSender(),notification.getReciever()), // notification object
             notification.getReciever()                    // username
    );
    notificationService.notify(
            new Notification(notification.getMessage(),notification.getSender(),notification.getReciever()), // notification object
            notification.getSender()                     // username
    );
    return "notifications";
  }

  /**
   * POST  /some-action  -> do an action.
   * 
   * After the action is performed will be notified UserA.
   */
  @RequestMapping(value = "/getUser", method = RequestMethod.GET)
  @ResponseBody
  public String getUser(Principal principal) {


   return principal.getName();
  }

  @RequestMapping(value = "/getOnlineUsers", method = RequestMethod.GET)
  @ResponseBody
  public List<String> getOnlineusers(Principal principal) {

    List<Object> principals = sessionRegistry.getAllPrincipals();

    List<String> usersNamesList = new ArrayList<String>();

    for (Object prin : principals) {

        usersNamesList.add(((org.springframework.security.core.userdetails.User) prin).getUsername());

    }
    return usersNamesList;
  }

} // class MainController
