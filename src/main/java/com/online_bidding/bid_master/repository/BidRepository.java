package com.online_bidding.bid_master.repository;

import com.online_bidding.bid_master.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findByAuctionIdOrderByAmountDesc(Long auctionId);
    Optional<Bid> findTopByAuctionIdOrderByAmountDesc(Long auctionId);
    List<Bid> findDistinctByBidderEmail(String email);
}
