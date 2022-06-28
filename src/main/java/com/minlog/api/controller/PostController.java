package com.minlog.api.controller;

import com.minlog.api.domain.User;
import lombok.*;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class PostController {

    @GetMapping("/home")
    public String getHome() {
        return "request home";
    }


    @PostMapping("/home")
    public UserDto postHome(@RequestBody @Valid User user) {
        return new UserDto(user.getUserId(), user.getPassword());
    }

    @Data
    @AllArgsConstructor
    static class UserDto {
        private String id;
        private String pwd;
    }
}

