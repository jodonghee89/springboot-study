package study.springboot.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static study.springboot.user.Role.RoleEnum.ADMIN;
import static study.springboot.user.Role.RoleEnum.USER;

@SpringBootTest
@WithMockUser(username = "test_user")
@Transactional
public class AccountServiceTest {
    @Autowired
    UserRepository repository;
    UserService service;

    @BeforeEach
    void before() {
        service = new UserService(repository);

        User account = new User();
        account.username = "test_username";
        account.password = "test_password";
        account.roles.add(ADMIN);
        account.roles.add(USER);
        repository.save(account);

        createAccountsForTestPaging();
    }

    private void createAccountsForTestPaging() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User account = new User("" + i, ""+i, new UserInfo(), USER);
            list.add(account);
        }
        repository.saveAll(list);
    }

    @Test
    void testPage() {
        PageRequest page = PageRequest.of(0, 10);
        UserSearchDto dto = new UserSearchDto("admin", ADMIN);
        Page<UserDto> accountDtoPage = service.page(page, dto);
        assertThat(accountDtoPage.getTotalElements()).isEqualTo(1);
    }
}

