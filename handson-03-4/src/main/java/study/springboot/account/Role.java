package study.springboot.account;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    @ManyToOne
    @JsonIgnore
    Account account;
    @Enumerated(STRING)
    public RoleEnum name;

    public Role() {}

    public Role(Account account, RoleEnum name) {
        this.account = account;
        this.name = name;
    }

    public enum RoleEnum {
        ADMIN, USER
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) && name == role.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
