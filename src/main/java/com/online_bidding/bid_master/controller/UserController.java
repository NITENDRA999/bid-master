package com.online_bidding.bid_master.controller;

import com.online_bidding.bid_master.dto.LoginRequestDTO;
import com.online_bidding.bid_master.dto.UserResponseDTO;
import com.online_bidding.bid_master.entity.User;
import com.online_bidding.bid_master.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public UserResponseDTO register(@Valid @RequestBody User user){
        return userService.registerUser(user);
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO request){
        return userService.login(request.getEmail(), request.getPassword());
    }
    @GetMapping("/test")
    public String test(){
        return "testing api";
    }
}
