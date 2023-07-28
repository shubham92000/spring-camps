package com.bootcamp.springcamp.runners;

import com.bootcamp.springcamp.models.User;
import com.bootcamp.springcamp.repos.UserRepo;
import com.bootcamp.springcamp.utils.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

@Component
public class DataSeeder implements CommandLineRunner {
    private UserRepo userRepo;

    public DataSeeder(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void run(String... args) throws Exception {
//        List<Role> roles = List.of(Role.user);
//        User u1 = new User();
//        u1.setName("john");
//        u1.setEmail("john@gmail.com");
//        u1.setRoles(roles);
//        u1.setPassword("123456");
//        u1 = userRepo.save(u1);
//        System.out.println(u1);

//        User u1 = userRepo.findByEmail("DEF@gmail.com").orElseThrow(() -> new RuntimeException("not found"));
//        System.out.println(u1);
//        System.out.println(LocalDateTime.now());

//        Calendar calendar = Calendar.getInstance();
//        System.out.println("-------------> "+calendar.getTimeZone());
    }
}
