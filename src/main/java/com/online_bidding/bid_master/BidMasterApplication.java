package com.online_bidding.bid_master;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BidMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(BidMasterApplication.class, args);
        System.out.println("hello world");
	}

}
