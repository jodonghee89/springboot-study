package study.springboot.user;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;

//@Entity
@Embeddable
public class UserInfo {
//    @Id
//    @GeneratedValue(strategy = IDENTITY)
//    Long id;
    String name = "";
    int age = 0;
    @Enumerated(STRING)
    Gender gender;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderBy("createdAt")
    List<Email> email = new ArrayList<>();

    public enum Gender {
        MALE, FEMALE, OTHER
    }
}
