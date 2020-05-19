package study.springboot.account;

import study.springboot.common.domain.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static javax.persistence.EnumType.STRING;

@Entity
public  class Account extends BaseEntity {
    @Column(unique = true)
    @NotBlank
    public String username = "";
    @NotBlank
    public String password = "";
    @ElementCollection
    @Enumerated(STRING)
    public Set<Role.RoleEnum> roles = new HashSet<>();
//    @OneToOne(fetch = FetchType.LAZY, mappedBy = "account", orphanRemoval = true, cascade = CascadeType.ALL)
    @Embedded
    public UserInfo userInfo = new UserInfo();

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Account() {}

    public Account(String username, String password, UserInfo userInfo, Role.RoleEnum... roles) {
        this.username = username;
        this.password = password;
        this.userInfo = userInfo;
        this.roles = Set.of(roles);
    }

    public AccountDto toDto() {
        AccountDto dto = new AccountDto();
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

    void update(AccountDto dto) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}