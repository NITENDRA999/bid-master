package com.online_bidding.bid_master.service;

import com.online_bidding.bid_master.dto.AuctionResponseDTO;
import com.online_bidding.bid_master.dto.BidRequestDTO;
import com.online_bidding.bid_master.dto.BidResponseDTO;
import com.online_bidding.bid_master.entity.Auction;
import com.online_bidding.bid_master.entity.AuctionStatus;
import com.online_bidding.bid_master.entity.Bid;
import com.online_bidding.bid_master.entity.User;
import com.online_bidding.bid_master.exception.AuctionNotFoundException;
import com.online_bidding.bid_master.exception.BidException;
import com.online_bidding.bid_master.exception.UserNotFoundException;
import com.online_bidding.bid_master.repository.AuctionRepository;
import com.online_bidding.bid_master.repository.BidRepository;
import com.online_bidding.bid_master.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BidService {
    private final BidRepository bidRepository;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;

    public String placeBid(BidRequestDTO request, String userEmail){

        Auction auction = auctionRepository.findById(request.getAuctionId())
                .orElseThrow(()->new AuctionNotFoundException("auction not found"));

        if(auction.getStatus() != AuctionStatus.ACTIVE){
            throw new RuntimeException("Auction is not active");
        }
        if (LocalDateTime.now().isAfter(auction.getEndTime())) {

            auction.setStatus(AuctionStatus.CLOSED);
            bidRepository
                    .findTopByAuctionIdOrderByAmountDesc(auction.getId())
                            .ifPresent(highestBid -> auction.setWinner(highestBid.getBidder())
                            );
            auctionRepository.save(auction);

            throw new RuntimeException("Auction has ended");
        }

        User bidder = userRepository.findByEmail(userEmail)
                .orElseThrow(()->new UserNotFoundException("user not found"));

        if(auction.getSeller().getId().equals(bidder.getId())){
            throw new BidException("you can not bid on own Auction");
        }
        if(request.getAmount() <= auction.getCurrentPrice()){
            throw new BidException("bid amount must be greater then current amount");
        }

        LocalDateTime now = LocalDateTime.now();
        Duration timeRemaining = Duration.between(now, auction.getEndTime());
        if(timeRemaining.getSeconds() <= 30){

            if(auction.getExtensionCount() < 5){
                auction.setEndTime(auction.getEndTime().plusMinutes(2));
                auction.setExtensionCount(auction.getExtensionCount()+1);
            }
        }

        Bid bid = Bid.builder()
                .amount(request.getAmount())
                .bidTime(LocalDateTime.now())
                .bidder(bidder)
                .auction(auction)
                .build();
        auction.setCurrentPrice(request.getAmount());
        bidRepository.save(bid);
        auctionRepository.save(auction);

        return "Bid placed successfully";
    }
    public List<BidResponseDTO> getBidsForAuction(Long auctionId){
        List<Bid> bids = bidRepository.findByAuctionIdOrderByAmountDesc(auctionId);

        return bids.stream()
                .map(bid -> BidResponseDTO.builder()
                        .amount(bid.getAmount())
                        .bidderName(bid.getBidder().getFullName())
                        .bidTime(bid.getBidTime())
                        .build())
                .toList();
    }
    public List<AuctionResponseDTO> getParticipatedAuctions(String email) {

        List<Bid> bids = bidRepository.findDistinctByBidderEmail(email);

        return bids.stream()
                .map(Bid::getAuction)
                .distinct()
                .map(auction -> AuctionResponseDTO.builder()
                        .id(auction.getId())
                        .title(auction.getTitle())
                        .description(auction.getDescription())
                        .currentPrice(auction.getCurrentPrice())
                        .startTime(auction.getStartTime())
                        .endTime(auction.getEndTime())
                        .status(auction.getStatus().name())
                        .categoryName(auction.getCategory().getName())
                        .sellerName(auction.getSeller().getFullName())
                        .build())
                .toList();
    }
}
