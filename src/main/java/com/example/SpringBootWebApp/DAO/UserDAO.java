package com.example.SpringBootWebApp.DAO;

import com.example.SpringBootWebApp.Models.User;
import com.example.SpringBootWebApp.UserStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDAO {
    private final List<User> db = new ArrayList<>();




    //////// потом удалить
    public UserDAO() {
        db.add(new User("Galina","Kim", UserStatus.MANAGER, "kim",0));
        db.add(new User("Anna","Karenina", UserStatus.WORKER, "Karenina",1));
        db.add(new User("Alia","ost", UserStatus.WORKER,"ost",2));
        db.add(new User("NNNN","MMM", UserStatus.WORKER, "MMM",3));
    }


    /// logic of connecting and operating with database

    public boolean addUser(User user) { // return true if all was success
        System.out.println("user added to db");
        // logic with db
        db.add(user);
        user.setId(db.size() - 1);
        return true;
    }

    public User findById(int id) {
        User user = db.get(id);
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

    public List<User> getAllWorkers() {
        List<User> workers = new ArrayList<>();
        for(User person : db) {
            if(person.getStatus()== UserStatus.WORKER) {
                workers.add(person);
            }
        }
        return workers;
    }
    public List<User> getAllManagers() {
        List<User> managers = new ArrayList<>();
        for(User person : db) {
            if(person.getStatus()== UserStatus.MANAGER) {
                managers.add(person);
            }
        }
        return managers;
    }

}
