package com.online_bidding.bid_master.controller;

import com.online_bidding.bid_master.dto.AuctionRequestDTO;
import com.online_bidding.bid_master.dto.AuctionResponseDTO;
import com.online_bidding.bid_master.service.AuctionService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/active")
    public List<AuctionResponseDTO> getActiveAuctions(){
        return auctionService.getActiveAuction();
    }
    @GetMapping("/{auctionId}")
    public AuctionResponseDTO getAuctionsDetails(@PathVariable Long auctionId){
        return auctionService.getAuctionById(auctionId);
    }
    @GetMapping("/my-auctions")
    public List<AuctionResponseDTO> getMyAuctions(Authentication authentication){
        String email = authentication.getName();
        return auctionService.getMyAuctions(email);
    }
}
