package study.springboot.user;

import study.springboot.user.Role.RoleEnum;

import java.util.Set;

public class UserSearchDto {
    private String username;
    private RoleEnum role;

    private static Set<RoleEnum> ROLE_ALL = Set.of(RoleEnum.values());

    public UserSearchDto() { }

    public UserSearchDto(String username, RoleEnum role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username == null ? "" : username;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public Set<RoleEnum> getSearchRole() {
        if(role == null)
            return ROLE_ALL;
        else
            return Set.of(role);
    }
}
