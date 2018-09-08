package netgloo.controllers;

import netgloo.Notification;
import netgloo.services.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.Response;
import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Controller
public class MainController {

  @Autowired
  private NotificationService notificationService;

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

    System.out.println("called");
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
  public String someAction(Principal principal) {


   return principal.getName();
  }

} // class MainController
