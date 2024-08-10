package org.example.serviceauth.data;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Column;

@Getter
@Setter
@Table("users")
public class User {
    @Id
    private String id;
    private String username;
    @Column()
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
