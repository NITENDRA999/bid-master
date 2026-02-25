package com.online_bidding.bid_master.controller;

import com.online_bidding.bid_master.entity.Auction;
import com.online_bidding.bid_master.entity.AuctionStatus;
import com.online_bidding.bid_master.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AuctionRepository auctionRepository;

    @PutMapping("/approve/{auctionId}")
    public String approveAuction(@PathVariable Long auctionId){
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(()->new RuntimeException("Auction not found"));
        auction.setStatus(AuctionStatus.ACTIVE);
        auctionRepository.save(auction);
        return "Auction approved successfully";
    }
}
