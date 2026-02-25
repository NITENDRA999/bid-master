package com.online_bidding.bid_master.dto;

import lombok.Data;

@Data
public class AuctionRequestDTO {
    private String title;
    private String description;
    private Double startingPrice;
    private Long categoryId;
    private int durationMinutes;
}
