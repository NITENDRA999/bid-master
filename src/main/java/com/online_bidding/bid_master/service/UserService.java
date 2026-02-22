package com.online_bidding.bid_master.service;

import com.online_bidding.bid_master.entity.AccountStatus;
import com.online_bidding.bid_master.entity.Role;
import com.online_bidding.bid_master.entity.User;
import com.online_bidding.bid_master.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User registerUser(User user){
        user.setRole(Role.USER);
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }
}
