package com.smartit.truckprojobs.controller;

import com.smartit.truckprojobs.model.Users;
import com.smartit.truckprojobs.model.UsersType;
import com.smartit.truckprojobs.service.UsersService;
import com.smartit.truckprojobs.service.UsersTypeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UsersRestController {

    private final UsersTypeService usersTypeService;
    private final UsersService usersService;

    public UsersRestController(UsersTypeService usersTypeService, UsersService usersService) {
        this.usersTypeService = usersTypeService;
        this.usersService = usersService;
    }

    @GetMapping("/types")
    public ResponseEntity<List<UsersType>> getAllUserTypes() {
        List<UsersType> usersTypes = usersTypeService.getAll();
        return ResponseEntity.ok(usersTypes);
    }

    @PostMapping("/register")
    public ResponseEntity<?> userRegistration(@Valid @RequestBody Users users) {
        Optional<Users> optionalUsers = usersService.getUserByEmail(users.getEmail());
        if (optionalUsers.isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered, try to login or register with another email.");
        }
        usersService.addNew(users);
        return ResponseEntity.ok("User registered successfully.");
    }

    @GetMapping("/{email}")
    public ResponseEntity<Users> getUserByEmail(@PathVariable String email) {
        Optional<Users> optionalUsers = usersService.getUserByEmail(email);
        return optionalUsers.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}