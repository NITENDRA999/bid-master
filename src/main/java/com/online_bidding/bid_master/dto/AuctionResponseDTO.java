package com.online_bidding.bid_master.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AuctionResponseDTO {

    private Long id;
    private String title;
    private String description;
    private Double startingPrice;
    private Double currentPrice;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private String categoryName;
    private String sellerName;
}
