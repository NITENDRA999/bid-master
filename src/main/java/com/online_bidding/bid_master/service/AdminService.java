package com.online_bidding.bid_master.service;

import com.online_bidding.bid_master.dto.AuctionResponseDTO;
import com.online_bidding.bid_master.entity.Auction;
import com.online_bidding.bid_master.entity.AuctionStatus;
import com.online_bidding.bid_master.exception.AuctionNotFoundException;
import com.online_bidding.bid_master.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AuctionRepository auctionRepository;
    public String approveAuction(Long auctionId){

        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(()->new AuctionNotFoundException("auction not found"));

        auction.setStatus(AuctionStatus.ACTIVE);

        auctionRepository.save(auction);

        return "auction approved successfully";
    }
    public List<AuctionResponseDTO> getPendingAuctions() {

        List<Auction> auctions =
                auctionRepository.findByStatus(AuctionStatus.PENDING_APPROVAL);

        return auctions.stream()
                .map(auction -> AuctionResponseDTO.builder()
                        .id(auction.getId())
                        .title(auction.getTitle())
                        .description(auction.getDescription())
                        .sellerName(auction.getSeller().getFullName())
                        .categoryName(auction.getCategory().getName())
                        .status(auction.getStatus().name())
                        .build())
                .toList();
    }
    public String rejectAuction(Long auctionId) {

        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() ->
                        new AuctionNotFoundException("Auction not found"));

        auction.setStatus(AuctionStatus.REJECTED);

        auctionRepository.save(auction);

        return "Auction rejected successfully";
    }
}
