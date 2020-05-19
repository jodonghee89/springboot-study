package study.springboot.user;

import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

class UserDto {
    Long id;
    @Size(min = 2, max = 20, message="사용자명은 2 ~ 20자를 입력해주세요.")
    String username = "";
    @Size(min = 2, max = 20, message="비밀번호는 2 ~ 20자를 입력해주세요.")
    String password = "";
    @Size(min=1, max = 2, message = "롤은 최소 1개 이상을 선택해주세요.")
    Set<String> roles = new HashSet<>();
    String name;
    int age;
    String gender;
    String createdBy;
    String createdAt;
    String updatedBy;
    String updatedAt;

    public UserDto() { }

    User createUser() {
        User account = new User();
        account.id = this.id == 0L ? null : this.id;
        account.username = this.username;
        account.password = this.password;
        account.roles = this.roles.stream().map(x -> Role.RoleEnum.valueOf(x)).collect(toSet());
        return account;
    }

    public long getId() {
        return id == null ? 0L : id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTextOfRoles() {
        return roles.stream().collect(joining(", "));
    }
}
