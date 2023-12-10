package com.example.SpringBootWebApp.DAO;

import com.example.SpringBootWebApp.Models.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDAO {
    private final List<User> db = new ArrayList<>();


    /// logic of connecting and operating with database

    public boolean addUser(User user) { // return true if all was success
        System.out.println("user added to db");
        // logic with db
        db.add(user);
        user.setId(db.size());
        return true;
    }

    public User findById(int id) {
        User user = db.get(id-1);
        System.out.println(user.getId());
        return user;
    }

    public User findUser(User user) { // здесь у пользователя усть только имя фамилия и password по гим и искать
        for(User person : db) {
            if(person.getPassword().equals(user.getPassword())) {
                return person;
            }
        }
        return null;
    }
}
