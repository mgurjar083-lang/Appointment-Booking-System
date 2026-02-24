package com.mynk.controller;

import com.mynk.modal.User;
import com.mynk.service.UserServiceImp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final UserServiceImp service;

    @PostMapping("/api/createUser")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user){
       User createdUser = service.createUser(user);
       return new ResponseEntity<>(createdUser ,HttpStatus.CREATED);
    }

    @GetMapping("/api/findAllUser")
    public ResponseEntity<List<User>> getAllUsers(){
      List<User> users = service.getAllUsers();
      return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/api/getUser/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
       User user = service.getUserById(id);
       return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/api/updateUser/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long id){
        User updatedUser = service.updateUser(user, id);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/api/deleteUser/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id){
       service.deleteUser(id);
       return new ResponseEntity<>("User Deleted successfully", HttpStatus.ACCEPTED);
    }
}
