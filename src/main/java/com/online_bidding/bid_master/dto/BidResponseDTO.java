package com.online_bidding.bid_master.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BidResponseDTO {

    private Double amount;
    private String bidderName;
    private LocalDateTime bidTime;
}
