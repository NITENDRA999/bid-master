package com.online_bidding.bid_master.scheduler;

import com.online_bidding.bid_master.entity.Auction;
import com.online_bidding.bid_master.entity.AuctionStatus;
import com.online_bidding.bid_master.repository.AuctionRepository;
import com.online_bidding.bid_master.repository.BidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuctionScheduler {

    private final BidRepository bidRepository;
    private final AuctionRepository auctionRepository;

    @Scheduled(fixedRate = 60000)
    public void closeExpiredAuction(){
        List<Auction> activeAuction = auctionRepository.findByStatus(AuctionStatus.ACTIVE);
        for(Auction auction : activeAuction){
            if(LocalDateTime.now().isAfter(auction.getEndTime())){
                auction.setStatus(AuctionStatus.CLOSED);

                bidRepository
                        .findTopByAuctionIdOrderByAmountDesc(auction.getId())
                        .ifPresent(bid ->
                                auction.setWinner(bid.getBidder())
                        );
                auctionRepository.save(auction);

                System.out.println("auction closed successfully" + auction.getId());
            }
        }
    }
}
