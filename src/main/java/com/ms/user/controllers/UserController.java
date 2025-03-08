package com.ms.user.controllers;

import com.ms.user.dtos.UserRecordDto;
import com.ms.user.models.User;
import com.ms.user.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody @Valid UserRecordDto userRecordDto) {

        var user = new User();
        // Convert userRecordDto to User
        BeanUtils.copyProperties(userRecordDto, user);


        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }
}
