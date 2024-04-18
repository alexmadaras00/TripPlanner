package org.example.serviceauth.data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("users")
public class User {

    @Id
    private Long id;
    private String username;
    private String password;

}
