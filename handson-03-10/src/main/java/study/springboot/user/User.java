package study.springboot.user;

import org.hibernate.envers.Audited;
import study.springboot.common.domain.BaseEntity;
import study.springboot.common.dto.AccountDto;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static javax.persistence.EnumType.STRING;

@Entity
// TODO
public class User extends BaseEntity {
    @Column(unique = true)
    @NotBlank
    String username = "";
    @NotBlank
    String password = "";
    @ElementCollection
    @Enumerated(STRING)
    Set<Role.RoleEnum> roles = new HashSet<>();
//    @OneToOne(fetch = FetchType.LAZY, mappedBy = "account", orphanRemoval = true, cascade = CascadeType.ALL)
    @Embedded
    UserInfo userInfo = new UserInfo();

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public User() {}

    public User(String username, String password, UserInfo userInfo, Role.RoleEnum... roles) {
        this.username = username;
        this.password = password;
        this.userInfo = userInfo;
        this.roles = Set.of(roles);
    }

    public UserDto toDto() {
        UserDto dto = new UserDto();
        dto.id = id;
        dto.username = username;
        dto.password = password;
        dto.roles = roles.stream().map(Enum::name).collect(toSet());
        dto.createdBy = createdBy;
        dto.createdAt = formatter.format(createdAt);
        dto.updatedBy = updatedBy;
        dto.updatedAt = formatter.format(updatedAt);
        return dto;
    }

    void update(UserDto dto) {
        this.username = dto.username;
        this.password = dto.password;
        roles.clear();
        for (String role : dto.roles) {
            roles.add(Role.RoleEnum.valueOf(role));
        }
    }

    public boolean isEqualUsername(String username) {
        return username != null && username.equals(this.username);
    }


    public AccountDto getAccount() {
        return new AccountDto(username, password, roles);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User account = (User) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
