package com.online_bidding.bid_master.service;

import com.online_bidding.bid_master.dto.UserResponseDTO;
import com.online_bidding.bid_master.entity.AccountStatus;
import com.online_bidding.bid_master.entity.Role;
import com.online_bidding.bid_master.entity.User;
import com.online_bidding.bid_master.repository.UserRepository;
import com.online_bidding.bid_master.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String login(String email, String password){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("user not fount"));
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException("invalid password");
        }
        return jwtService.generateToken(user.getEmail());
    }

    public UserResponseDTO registerUser(User user){
        if (userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new RuntimeException("Email already exist");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        return UserResponseDTO.builder()
                .id(savedUser.getId())
                .fullName(savedUser.getFullName())
                .email(savedUser.getEmail())
                .phoneNumber(savedUser.getPhoneNumber())
                .address(savedUser.getAddress())
                .role(savedUser.getRole().name())
                .build();
    }
}
