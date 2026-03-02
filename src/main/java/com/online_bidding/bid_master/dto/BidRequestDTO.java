package com.online_bidding.bid_master.dto;

import lombok.Data;

@Data
public class BidRequestDTO {
    private Long auctionId;
    private Double amount;
}
