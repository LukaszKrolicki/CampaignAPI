package com.app.campaignapi.repositories;
import com.app.campaignapi.domain.Entities.Product;
import com.app.campaignapi.domain.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findAllBySeller(User user);
}