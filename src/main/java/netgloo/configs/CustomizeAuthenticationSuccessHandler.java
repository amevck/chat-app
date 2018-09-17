package netgloo.configs;


import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netgloo.beans.GenericMessage;
import netgloo.enums.MessageType;
import netgloo.models.Message;
import netgloo.models.User;
import netgloo.repos.UserRepository;
import netgloo.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        //set our response to OK status
//        response.setStatus(HttpServletResponse.SC_OK);
//
//        for (GrantedAuthority auth : authentication.getAuthorities()) {
//            if ("ADMIN".equals(auth.getAuthority())) {
//                response.sendRedirect("/dashboard");
//            }
//        }

        try {
            List<Object> principals = sessionRegistry.getAllPrincipals();

            List<String> usersNamesList = new ArrayList<String>();
            User user = userRepository.findByEmail(authentication.getName());
            for (Object prin : principals) {

                   if(!((org.springframework.security.core.userdetails.User) prin).getUsername().equals(authentication.getName())) {
                       notificationService.notify(
                               new GenericMessage<User>(user, MessageType.USER), // notification object
                               ((org.springframework.security.core.userdetails.User) prin).getUsername()                    // username
                       );

                   }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
