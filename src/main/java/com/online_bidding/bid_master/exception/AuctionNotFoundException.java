package com.online_bidding.bid_master.exception;

public class AuctionNotFoundException extends RuntimeException{
    public AuctionNotFoundException (String message){
        super(message);
    }
}
