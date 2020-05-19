package study.springboot.user;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Audited
@org.hibernate.annotations.Cache(region = "entity.user.email", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Email {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    @ManyToOne
    User account;
    String email;
    boolean isValid;
    boolean isDeleted;
    @CreatedDate
    LocalDateTime createdAt;

    public Email() { }

    Email(String email) {
        this.email = email;
        this.isValid = false;
        this.isDeleted = false;
    }

}
