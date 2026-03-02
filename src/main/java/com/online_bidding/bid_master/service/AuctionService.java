package com.online_bidding.bid_master.service;

import com.online_bidding.bid_master.dto.AuctionRequestDTO;
import com.online_bidding.bid_master.dto.AuctionResponseDTO;
import com.online_bidding.bid_master.entity.Auction;
import com.online_bidding.bid_master.entity.AuctionStatus;
import com.online_bidding.bid_master.entity.Category;
import com.online_bidding.bid_master.entity.User;
import com.online_bidding.bid_master.exception.AuctionNotFoundException;
import com.online_bidding.bid_master.exception.UserNotFoundException;
import com.online_bidding.bid_master.repository.AuctionRepository;
import com.online_bidding.bid_master.repository.CategoryRepository;
import com.online_bidding.bid_master.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final AuctionRepository auctionRepository;

    public AuctionResponseDTO createAuction(AuctionRequestDTO request, String userEmail){
        User seller = userRepository.findByEmail(userEmail)
                .orElseThrow(()->new UserNotFoundException("User not found"));
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

        return  mapToDTO(auction);
    }


    public List<AuctionResponseDTO> getActiveAuction(){
        List<Auction> auctions = auctionRepository.findByStatus(AuctionStatus.ACTIVE);
        return auctions.stream()
                .map(this::mapToDTO)
                .toList();
    }

    public AuctionResponseDTO getAuctionById(Long auctionId){
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(()-> new AuctionNotFoundException("auction Not found"));
        return mapToDTO(auction);
    }

    public List<AuctionResponseDTO> getMyAuctions(String email){
        List<Auction> auctions = auctionRepository.findBySellerEmail(email);
        return auctions.stream()
                .map(this::mapToDTO)
                .toList();
    }

    //mapping from auction to dto
    private AuctionResponseDTO mapToDTO(Auction auction) {

        LocalDateTime now = LocalDateTime.now();

        long remainingSeconds =
                Duration.between(now, auction.getEndTime()).getSeconds();

        if (remainingSeconds < 0) {
            remainingSeconds = 0;
        }

        String winnerName = auction.getWinner() != null
                ? auction.getWinner().getFullName()
                : null;

        return AuctionResponseDTO.builder()
                .id(auction.getId())
                .title(auction.getTitle())
                .description(auction.getDescription())
                .startingPrice(auction.getStartingPrice())
                .currentPrice(auction.getCurrentPrice())
                .startTime(auction.getStartTime())
                .endTime(auction.getEndTime())
                .status(auction.getStatus().name())
                .categoryName(auction.getCategory().getName())
                .sellerName(auction.getSeller().getFullName())
                .winnerName(winnerName)
                .remainingSeconds(remainingSeconds)
                .expired(remainingSeconds == 0)
                .build();
    }
}
