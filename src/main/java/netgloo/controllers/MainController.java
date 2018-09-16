package netgloo.controllers;

import netgloo.Notification;
import netgloo.models.Message;
import netgloo.models.User;
import netgloo.repos.MessageRepository;
import netgloo.services.MessageService;
import netgloo.services.NotificationService;

import netgloo.services.UserService;
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
  private UserService userService;


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
  public String notifications(Principal principal,Notification notification) {

    messageService.SendMessage(principal,notification);
    return "notifications";
  }

  /**
   * POST  /some-action  -> do an action.
   * 
   * After the action is performed will be notified UserA.
   */
  @RequestMapping(value = "/getUser", method = RequestMethod.GET)
  @ResponseBody
  public User getUser(Principal principal) {
      if(principal != null) {
          User user = userService.getUserByEmail(principal.getName());
          user.setPassword("");
          return user;
      }
      return null;
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
