package com.app.campaignapi.repositories;

import com.app.campaignapi.domain.Entities.Keyword_;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword_, UUID> {
    Keyword_ findByCvalue(String value);
}

