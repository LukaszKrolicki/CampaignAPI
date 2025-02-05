package com.app.campaignapi.repositories;
import com.app.campaignapi.domain.Entities.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TownRepository extends JpaRepository<Town, UUID> {
    Optional<Town> findByName(String townName);
}
