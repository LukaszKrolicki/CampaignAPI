package com.app.campaignapi.config;

import com.app.campaignapi.domain.Dtos.*;
import com.app.campaignapi.services.impl.entry.EntryService;
import com.app.campaignapi.services.impl.UserService;
import com.app.campaignapi.services.impl.AdminService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(EntryService entryService, UserService userService, AdminService adminService) {
        return args -> {
            if (!entryService.userExists("a1")) {
                UserDTO user1 = UserDTO.builder()
                        .email("a1")
                        .password("a1")
                        .balance(BigDecimal.valueOf(125.50))
                        .role("ADMIN")
                        .build();
                entryService.createUser(user1);
            }

            if (!entryService.userExists("u1")) {
                UserDTO user2 = UserDTO.builder()
                        .email("u1")
                        .password("u1")
                        .balance(BigDecimal.valueOf(125.0))
                        .role("USER")
                        .build();
                entryService.createUser(user2);
            }

            TownDTO town1 = new TownDTO();
            town1.setName("Town1");
            TownDTO town2 = new TownDTO();
            town2.setName("Town2");
            TownDTO town3 = new TownDTO();
            town3.setName("Cracow");
            adminService.addTown(town1);
            adminService.addTown(town2);
            adminService.addTown(town3);

            KeywordDTO keyword1 = new KeywordDTO();
            keyword1.setCvalue("Keyword1");
            KeywordDTO keyword2 = new KeywordDTO();
            keyword2.setCvalue("Keyword2");
            KeywordDTO keyword3 = new KeywordDTO();
            keyword3.setCvalue("Jewelery");
            adminService.addKeyword(keyword1);
            adminService.addKeyword(keyword2);
            adminService.addKeyword(keyword3);

            keyword1 = adminService.getKeywordByValue("Keyword1");
            keyword2 = adminService.getKeywordByValue("Keyword2");

            UUID userId = entryService.userExists("u1") ? entryService.getUserIdByEmail("u1") : null;
            if (userId != null) {
                ProductDTO product1 = new ProductDTO();
                product1.setName("Product1");
                userService.addProduct(userId, product1);

                ProductDTO product2 = new ProductDTO();
                product2.setName("Product2");
                userService.addProduct(userId, product2);
            }

            UUID productId = userService.getProducts(userId).get(0).getId();
            if (productId != null) {
                CampaignDTO campaign1 = new CampaignDTO();
                campaign1.setProductId(productId);
                campaign1.setCampaignName("Campaign1");
                campaign1.setBidAmount(BigDecimal.valueOf(10.0));
                campaign1.setCampaignFund(BigDecimal.valueOf(100.0));
                campaign1.setTownCname("Town1");
                campaign1.setStatus(true);
                campaign1.setRadius(5);
                Set<UUID> keywordIds = new HashSet<>(Arrays.asList(keyword1.getId(), keyword2.getId()));
                campaign1.setKeywordIds(keywordIds);
                userService.addCampaign(userId, campaign1);
            }
        };
    }
}