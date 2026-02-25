package com.online_bidding.bid_master.config;

import com.online_bidding.bid_master.entity.Category;
import com.online_bidding.bid_master.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;


    @Override
    public void run(String... args) throws Exception {
        if(categoryRepository.count() == 0){
            categoryRepository.save(Category.builder().name("Electronics").build());
            categoryRepository.save(Category.builder().name("Vehicles").build());
            categoryRepository.save(Category.builder().name("Furniture").build());
            categoryRepository.save(Category.builder().name("Books").build());
        }
    }
}
