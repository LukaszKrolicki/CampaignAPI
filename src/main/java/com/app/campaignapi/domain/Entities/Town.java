package com.app.campaignapi.domain.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "towns")
public class Town {
    @Id
    @GeneratedValue(generator="UUID")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;
}