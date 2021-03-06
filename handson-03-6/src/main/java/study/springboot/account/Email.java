package study.springboot.account;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Email {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    @ManyToOne
    Account account;
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
