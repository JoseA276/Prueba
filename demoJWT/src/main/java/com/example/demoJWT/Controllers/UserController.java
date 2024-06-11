package com.example.demoJWT.Controllers;

import com.example.demoJWT.Entities.User;
import com.example.demoJWT.Services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping
public class UserController {

    UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/usuarios")
    public ResponseEntity<Object> readAll() {
        List<User> users = userService.readAllUsers();

        return ResponseEntity.ok(users);

    }
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<User> readUserById(@PathVariable Integer id) {
        User user = userService.readUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/usuarios")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        try {

            User createdUser = userService.createUser(user);

            return ResponseEntity.ok(createdUser);
        } catch (DataIntegrityViolationException ex) {

            throw new RuntimeException("El DNI ya existe en la base de datos. Por favor, elija un DNI único.", ex);
        }
    }


    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User updatedUser) {
        User user = userService.updateUser(id, updatedUser);

        return ResponseEntity.ok(user);
    }
}