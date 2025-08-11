package com.project.Expenses.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.Expenses.model.User;
import com.project.Expenses.service.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/login";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password,
                            Model model, HttpSession session) {

        User user = userService.findByUsername(username);
        if (user != null && user.getPassword().equals(password) && user.getUsername().equals(username)) {
            session.setAttribute("user", user);
            model.addAttribute("successMessage", "Successfully Login!");

            // Admin role check
            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                return "redirect:/expenses/admin/dashboard";
            } else {
                return "redirect:/expenses";
            }
        }

        model.addAttribute("error", "Invalid username or password");
        return "login";
    }


    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password,
            @RequestParam String emailId, @RequestParam String fullName, Model model) {

        User users = userService.findByUsername(username);
        if (users != null && users.getUsername().equals(username)) {
            model.addAttribute("error", "You are already registered");
            return "register";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmailId(emailId);
        user.setFullName(fullName);
        userService.save(user);
        model.addAttribute("successMessage", "Successfully registerd!");
        return "redirect:/login";

    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}
