package sql;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.springboot.user.User;

import java.util.List;

import static study.springboot.user.Role.RoleEnum.ADMIN;
import static study.springboot.user.Role.RoleEnum.USER;

public class SqlGenerator {
    private PasswordEncoder encoder;

    public SqlGenerator(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public void run() {

        String sql = "";
        sql += String.format("insert into user (created_at, created_by, updated_at, updated_by, password, username) values (now(), 'system', now(), 'system', '%s', '%s');\n", encoder.encode("admin"), "admin");
        sql +="set @last_insert_id = (select last_insert_id());\n";
        sql += "insert into user_roles (roles, user_id) values ('ADMIN', @last_insert_id);\n";
        sql += "insert into user_roles (roles, user_id) values ('USER', @last_insert_id);\n";
        for (int i = 0; i < 100; i++) {
            sql += String.format("insert into user (created_at, created_by, updated_at, updated_by, password, username) values (now(), 'system', now(), 'system', '%s', '%s');\n", encoder.encode("user"+i), "user" + i);
            sql +="set @last_insert_id = (select last_insert_id());\n";
            sql += "insert into user_roles (roles, user_id) values ('USER', @last_insert_id);\n";
        }
        System.out.println(sql);
    }


    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        new SqlGenerator( encoder).run();
    }
}

