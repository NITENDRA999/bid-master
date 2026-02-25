package com.online_bidding.bid_master.repository;

import com.online_bidding.bid_master.entity.Auction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

}
