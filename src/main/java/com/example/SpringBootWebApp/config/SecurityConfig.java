package com.example.SpringBootWebApp.config;

import com.example.SpringBootWebApp.entity.User;
import com.example.SpringBootWebApp.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/login", "/logout", "/registration").permitAll() // Разрешаем доступ к странице входа всем пользователям
                        .anyRequest().authenticated() // Все остальные запросы требуют аутентификации
                )
                .formLogin(form -> form
                        .loginPage("/login") // Указываем страницу входа
                        .usernameParameter("email") // Указываем что username в форме это поле email
                        .permitAll() // Разрешаем доступ к форме входа всем пользователям
                        .successHandler((request, response, authentication) -> {
                            User user = (User) authentication.getPrincipal();
                            response.sendRedirect("/users/" + user.getId());
                        })
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/hello") // Устанавливаем обработчик успешного логаута
                );
        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return username -> {
            User user = userRepo.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User ‘" + username + "’ not found"));
            return user;
        };
    }


}
