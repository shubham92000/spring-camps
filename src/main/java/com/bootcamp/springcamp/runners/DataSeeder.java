package com.bootcamp.springcamp.runners;

import com.bootcamp.springcamp.models.User;
import com.bootcamp.springcamp.repos.UserRepo;
import com.bootcamp.springcamp.utils.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

//@Component
public class DataSeeder implements CommandLineRunner {
    private UserRepo userRepo;

    public DataSeeder(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Role> roles = List.of(Role.user);
        User u1 = new User("shubham", "shubham@gmail.com", roles, "123456", null, null, LocalDateTime.now());
        u1 = userRepo.save(u1);
        System.out.println(u1);
    }
}
