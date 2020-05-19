package study.springboot.event;

import org.springframework.context.event.EventListener;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.event.AuthorizedEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class SecurityEvent  {

    @EventListener
    public void handler(InteractiveAuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        System.out.println(event);
    }

    @EventListener
    public void handler(AuthorizedEvent event) {
        Authentication authentication = event.getAuthentication();
        Collection<ConfigAttribute> configAttributes = event.getConfigAttributes();
        System.out.println(event);
    }
}
