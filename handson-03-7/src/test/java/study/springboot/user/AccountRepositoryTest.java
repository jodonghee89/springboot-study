package study.springboot.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import study.springboot.config.jdbc.AuditorAwareConfig;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static study.springboot.user.Role.RoleEnum.ADMIN;
import static study.springboot.user.Role.RoleEnum.USER;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Rollback
@WithMockUser(username = "test_user")
@Import(AuditorAwareConfig.class)
public class AccountRepositoryTest {
    @Autowired
    UserRepository repository;

    @BeforeEach
    void before() {
        User account = new User();
        account.username = "test_username";
        account.password = "test_password";
        account.roles.add(ADMIN);
        account.roles.add(USER);
        repository.save(account);
    }

    @Test
    void testSelect() {
        List<User> list = repository.findAll();
        User account = list.get(0);
        assertAccount(account, "test_username", "test_password", "ADMIN", "USER");
        assertThat(account.createdBy).isEqualTo("test_user");
        assertThat(account.createdAt).isBefore(LocalDateTime.now());
        assertThat(account.updatedBy).isEqualTo("test_user");
        assertThat(account.updatedAt).isBefore(LocalDateTime.now());
    }

    private void assertAccount(User account, String username, String password, String... roles) {
        assertThat(account.isEqualUsername(username)).isTrue();
        assertThat(account.password).isEqualTo(password);
        List<Role.RoleEnum> roleList = Stream.of(roles).map(Role.RoleEnum::valueOf).collect(toList());
        for (Role.RoleEnum role : account.roles) {
            assertThat(roleList).contains(role);
        }
    }

    @Test
    void testInsert() {
        User account = new User();
        account.username = "hello";
        account.password = "world";
        account.roles = new HashSet<>();
        account.roles.add(USER);
        repository.save(account);

        User result = repository.findAll().get(1);
        assertAccount(result, "hello", "world", "USER");
        assertThat(account.createdBy).isEqualTo("test_user");
        assertThat(account.createdAt).isBefore(LocalDateTime.now());
        assertThat(account.updatedBy).isEqualTo("test_user");
        assertThat(account.updatedAt).isBefore(LocalDateTime.now());
    }

    @Test
    void testUpdate() {
        User account = repository.findAll().get(0);
        account.username = "new_admin";
        account.password = "new_password";
        account.roles.remove(ADMIN);
        repository.save(account);

        User result = repository.findAll().get(0);

        assertThat(result).isEqualTo(account);
        assertThat(account.createdBy).isEqualTo("test_user");
        assertThat(account.createdAt).isBefore(LocalDateTime.now());
        assertThat(account.updatedBy).isEqualTo("test_user");
        assertThat(account.updatedAt).isBefore(LocalDateTime.now());
        assertThat(account.roles.size()).isEqualTo(1);
        Role.RoleEnum roleEnum = account.roles.stream().findFirst().get();
        assertThat(roleEnum).isEqualTo(USER);

    }

    @Test
    void testDelete() {
        User account = repository.findAll().get(0);
        repository.deleteById(account.id);
        long count = repository.count();
        assertThat(count).isEqualTo(0);
    }

    @Test
    void testPaging() {
        createAccountsForTestPaging();
        PageRequest pageable = PageRequest.of(1, 10, Sort.Direction.DESC, "username");
        Page<User> page = repository.findAll(pageable);
        assertThat(page.getTotalElements()).isEqualTo(101);
        assertThat(page.getTotalPages()).isEqualTo(11);
        assertThat(page.hasPrevious()).isTrue();
        assertThat(page.getContent().get(0).username).isEqualTo("90");
    }

    @Test
    void testSearchPaging() {
        createAccountsForTestPaging();
        PageRequest pageable = PageRequest.of(1, 10, Sort.Direction.DESC, "username");
        Page<User> page = repository.findByUsernameContainingAndRolesIn(pageable, "1", Set.of(ADMIN, USER));
        assertThat(page.getTotalElements()).isEqualTo(19);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.hasPrevious()).isTrue();
        assertThat(page.hasNext()).isFalse();
        assertThat(page.getContent().get(0).username).isEqualTo("17");
    }

    private void createAccountsForTestPaging() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            User account = new User("" + i, ""+i, new UserInfo(), USER);
            list.add(account);
        }
        repository.saveAll(list);
    }
}
