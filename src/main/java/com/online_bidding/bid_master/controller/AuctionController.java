package com.online_bidding.bid_master.controller;

import com.online_bidding.bid_master.dto.AuctionRequestDTO;
import com.online_bidding.bid_master.dto.AuctionResponseDTO;
import com.online_bidding.bid_master.entity.Auction;
import com.online_bidding.bid_master.service.AuctionService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auctions")
@RequiredArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;

    @PostMapping
    public AuctionResponseDTO createAuction(@RequestBody AuctionRequestDTO request,
                                            Authentication authentication){
        String userEmail = authentication.getName();

        return auctionService.createAuction(request, userEmail);
    }
}
