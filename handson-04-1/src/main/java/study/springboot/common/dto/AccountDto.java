package study.springboot.common.dto;

import study.springboot.user.Role;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class AccountDto {
    public String username;
    public String password;
    public Set<String> roles;

    public AccountDto(String username, String password, Set<Role.RoleEnum> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles.stream().map(Enum::name).collect(toSet());
    }
}
