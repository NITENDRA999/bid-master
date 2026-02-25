package com.online_bidding.bid_master.service;

import com.online_bidding.bid_master.dto.AuctionRequestDTO;
import com.online_bidding.bid_master.dto.AuctionResponseDTO;
import com.online_bidding.bid_master.entity.Auction;
import com.online_bidding.bid_master.entity.AuctionStatus;
import com.online_bidding.bid_master.entity.Category;
import com.online_bidding.bid_master.entity.User;
import com.online_bidding.bid_master.repository.AuctionRepository;
import com.online_bidding.bid_master.repository.CategoryRepository;
import com.online_bidding.bid_master.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final AuctionRepository auctionRepository;

    public AuctionResponseDTO createAuction(AuctionRequestDTO request, String userEmail){
        User seller = userRepository.findByEmail(userEmail)
                .orElseThrow(()->new RuntimeException("User not found"));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(()->new RuntimeException("Category not exist"));
        LocalDateTime now = LocalDateTime.now();

        Auction auction = Auction.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .startingPrice(request.getStartingPrice())
                .currentPrice(request.getStartingPrice())
                .startTime(now)
                .endTime(now.plusMinutes(request.getDurationMinutes()))
                .status(AuctionStatus.PENDING_APPROVAL)
                .extensionCount(0)
                .seller(seller)
                .category(category)
                .build();
        Auction savedAuction = auctionRepository.save(auction);
        return AuctionResponseDTO.builder()
                .id(savedAuction.getId())
                .title(savedAuction.getTitle())
                .description(savedAuction.getDescription())
                .startingPrice(savedAuction.getStartingPrice())
                .currentPrice(savedAuction.getCurrentPrice())
                .startTime(savedAuction.getStartTime())
                .endTime(savedAuction.getEndTime())
                .status(savedAuction.getStatus().name())
                .categoryName(savedAuction.getCategory().getName())
                .sellerName(savedAuction.getSeller().getFullName())
                .build();
    }
}
