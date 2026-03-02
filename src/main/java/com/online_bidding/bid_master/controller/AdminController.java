package com.online_bidding.bid_master.controller;

import com.online_bidding.bid_master.dto.AuctionResponseDTO;
import com.online_bidding.bid_master.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/pending-auctions")
    public List<AuctionResponseDTO> getPendingAuctions() {
        return adminService.getPendingAuctions();
    }

    @PutMapping("/approve/{auctionId}")
    public String approveAuction(@PathVariable Long auctionId){
        return adminService.approveAuction(auctionId);
    }

    @PutMapping("/reject/{auctionId}")
    public String rejectAuction(@PathVariable Long auctionId) {
        return adminService.rejectAuction(auctionId);
    }
}
