package study.springboot.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static study.springboot.user.Role.RoleEnum.ADMIN;
import static study.springboot.user.Role.RoleEnum.USER;

@Component
@Transactional
@Profile("default")
public class DefaultProfileDataInitializer implements CommandLineRunner {
    final UserRepository repository;

    public DefaultProfileDataInitializer(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        List list = new ArrayList();
        for(int i=0; i <= 100; i++) {
            if(i == 0) createAccount("admin", list);
            else createAccount(format("test%03d", i), list);
        }
        repository.saveAll(list);
    }

    private void createAccount(String v, List list) {
        User account = new User();
        account.username = v;
        account.password = v;
        if(v.equals("admin")) account.roles.add(ADMIN);
        account.roles.add(USER);
        list.add(account);
    }
}
