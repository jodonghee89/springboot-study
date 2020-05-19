package study.springboot.user;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;

//@Entity
@Embeddable
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;
//    @Id
//    @GeneratedValue(strategy = IDENTITY)
//    Long id;
    String name = "";
    int age = 0;
    @Enumerated(STRING)
    Gender gender;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderBy("createdAt")
    @org.hibernate.annotations.Cache(region = "entity.user", usage = CacheConcurrencyStrategy.READ_WRITE)
    List<Email> email = new ArrayList<>();

    public enum Gender {
        MALE, FEMALE, OTHER
    }
}
