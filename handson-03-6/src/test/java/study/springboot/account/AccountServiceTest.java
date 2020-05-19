package study.springboot.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import study.springboot.config.jdbc.AuditorAwareConfig;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static study.springboot.account.Role.RoleEnum.ADMIN;
import static study.springboot.account.Role.RoleEnum.USER;

@SpringBootTest
@WithMockUser(username = "test_user")
@Transactional
public class AccountServiceTest {
    @Autowired AccountRepository repository;
    AccountService service;

    @BeforeEach
    void before() {
        service = new AccountService(repository);

        Account account = new Account();
        account.username = "test_username";
        account.password = "test_password";
        account.roles.add(ADMIN);
        account.roles.add(USER);
        repository.save(account);

        createAccountsForTestPaging();
    }

    private void createAccountsForTestPaging() {
        List<Account> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Account account = new Account("" + i, ""+i, new UserInfo(), USER);
            list.add(account);
        }
        repository.saveAll(list);
    }

    @Test
    void testPage() {
        PageRequest page = PageRequest.of(0, 10);
        AccountSearchDto dto = new AccountSearchDto("admin", ADMIN);
        Page<AccountDto> accountDtoPage = service.page(page, dto);
        assertThat(accountDtoPage.getTotalElements()).isEqualTo(1);
    }
}

