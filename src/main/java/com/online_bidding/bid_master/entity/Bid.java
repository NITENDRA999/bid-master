package com.online_bidding.bid_master.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "bids")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private LocalDateTime bidTime;

    @ManyToOne
    @JoinColumn(name = "bidder_id")
    private User bidder;

    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;
}
