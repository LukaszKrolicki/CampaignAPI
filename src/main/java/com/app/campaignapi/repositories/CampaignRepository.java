package com.app.campaignapi.repositories;

import com.app.campaignapi.domain.Entities.Campaign;
import com.app.campaignapi.domain.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, UUID> {
    List<Campaign> findAllByProduct_Seller(User user);
}
