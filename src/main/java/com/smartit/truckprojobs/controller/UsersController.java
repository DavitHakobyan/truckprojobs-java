package com.smartit.truckprojobs.controller;

import com.smartit.truckprojobs.model.Users;
import com.smartit.truckprojobs.model.UsersType;
import com.smartit.truckprojobs.service.UsersService;
import com.smartit.truckprojobs.service.UsersTypeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;
    private final UsersTypeService usersTypeService;

    public UsersController(UsersService usersService,
                           UsersTypeService usersTypeService) {
        this.usersService = usersService;
        this.usersTypeService = usersTypeService;
    }

//    @GetMapping
//    public List<Users> getAllUsers() {
//        return usersService.getAllUsers();
//    }
//
//    @GetMapping("/{id}")
//    public Optional<Users> getUserById(@PathVariable Long id) {
//        return usersService.getUserById(id);
//    }

    @PostMapping
    public Users createUser(@RequestBody Users user) {
        return usersService.saveUser(user);
    }
//
//    @DeleteMapping("/{id}")
//    public void deleteUserById(@PathVariable Long id) {
//        usersService.deleteUserById(id);
//    }


    @GetMapping("/register")
    public String register(Model model) {
        List<UsersType> usersTypes = usersTypeService.getAll();
        model.addAttribute("getAllTypes", usersTypes);
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register/new")
//    public String userRegistration(@Valid Users users, Model model) {
    public String userRegistration(@RequestBody Users users) { // , Model model) {
        Optional<Users> optionalUsers = usersService.getUserByEmail(users.getEmail());
//        if (optionalUsers.isPresent()) {
//            model.addAttribute("error", "Email already registered,try to login or register with other email.");
//            List<UsersType> usersTypes = usersTypeService.getAll();
//            model.addAttribute("getAllTypes", usersTypes);
//            model.addAttribute("user", new Users());
//            return "register";
//        }
        usersService.addNew(users);
        return "redirect:/dashboard/";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }
}