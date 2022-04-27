package com.benrkia.datagenerator.repository;

import com.benrkia.datagenerator.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
  long countByOwner_Username(String username);
}