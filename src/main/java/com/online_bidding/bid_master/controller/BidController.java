package com.online_bidding.bid_master.controller;

import com.online_bidding.bid_master.dto.AuctionResponseDTO;
import com.online_bidding.bid_master.dto.BidRequestDTO;
import com.online_bidding.bid_master.dto.BidResponseDTO;
import com.online_bidding.bid_master.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/bids")
@RequiredArgsConstructor
public class BidController {

    private final BidService bidService;

    @PostMapping
    public String placeBid(@RequestBody BidRequestDTO request, Authentication authentication){

        String email = authentication.getName();

        return bidService.placeBid(request, email);
    }
    @GetMapping("/auction/{auctionId}")
    public List<BidResponseDTO> getAuctionBids(@PathVariable Long auctionId){
        return bidService.getBidsForAuction(auctionId);
    }
    @GetMapping("/my-bids")
    public List<AuctionResponseDTO> getMyBids(
            Authentication authentication) {

        String email = authentication.getName();

        return bidService.getParticipatedAuctions(email);
    }
}
