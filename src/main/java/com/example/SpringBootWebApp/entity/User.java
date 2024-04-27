

package com.example.SpringBootWebApp.entity;

import com.example.SpringBootWebApp.enums.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements Serializable {
    public static final int NAME_MAX_LENGTH = 50;
    public static final int NAME_MIN_LENGTH = 2;
    public static final int EMAIL_MAX_LENGTH = 30;
    public static final int EMAIL_MIN_LENGTH = 8;
    public static final int PHONE_MAX_LENGTH = 20;
    public static final int PHONE_MIN_LENGTH = 6;
    public static final int PASSWORD_MIN_LENGTH = 6;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @Column(name = "first_name", nullable = false, length = User.NAME_MAX_LENGTH)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = User.NAME_MAX_LENGTH)
    private String lastName;

    @Column(nullable = false)
    private String password;

    @Column(length = User.EMAIL_MAX_LENGTH, unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "owner")
    private Set<Task> tasks;

}
