

package com.example.SpringBootWebApp.entity;

import com.example.SpringBootWebApp.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
public class User implements Serializable , UserDetails {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("Поулчаем роли " + status.toString());
        return List.of(new SimpleGrantedAuthority(status.toString()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
