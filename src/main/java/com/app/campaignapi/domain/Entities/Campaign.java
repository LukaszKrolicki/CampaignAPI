package com.app.campaignapi.domain.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "campaigns")
public class Campaign {
    @Id
    @GeneratedValue(generator="UUID")
    private UUID id;

    @Column(nullable = false)
    private String campaignName;

    @ManyToMany
    @JoinTable(
            name = "campaign_keywords",
            joinColumns = @JoinColumn(name = "campaign_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id")
    )
    private Set<Keyword_> keywords;

    @Column(nullable = false)
    private BigDecimal bidAmount;

    @Column(nullable = false)
    private BigDecimal campaignFund;

    @Column(nullable = false)
    private Boolean status = true;

    @ManyToOne
    @JoinColumn(name = "town_id", nullable = false)
    private Town town;

    @Column(nullable = false)
    private Integer radius;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
