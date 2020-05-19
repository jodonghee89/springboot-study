package study.springboot.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Audited
@org.hibernate.annotations.Cache(region = "entity.user.role", usage = CacheConcurrencyStrategy.READ_ONLY)
public class Role {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    @ManyToOne
    @JsonIgnore
    User account;
    @Enumerated(STRING)
    public RoleEnum name;

    public Role() {}

    public Role(User account, RoleEnum name) {
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
