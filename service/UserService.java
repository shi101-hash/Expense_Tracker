package com.project.Expenses.service;

import com.project.Expenses.model.User;
import com.project.Expenses.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class UserService implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    // Automatically create default admin
    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin123"); // You can change this
            admin.setEmailId("admin@company.com");
            admin.setFullName("Admin User");
            admin.setRole("ADMIN");

            userRepository.save(admin);
            System.out.println(" Default admin created: username=admin, password=admin123");
        }
    }
}
