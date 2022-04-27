package com.benrkia.datagenerator.service;

import com.benrkia.datagenerator.entity.Campaign;
import com.benrkia.datagenerator.entity.User;
import com.benrkia.datagenerator.repository.CampaignRepository;
import com.benrkia.datagenerator.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CampaignService {

  private final CampaignRepository campaignRepository;

  public Campaign saveCampaign(final Campaign campaign) {
    log.info("Saving new campaign {}", campaign);
    return campaignRepository.save(campaign);
  }

  public long countAllCampaigns() {
    return campaignRepository.count();
  }

  public long countUserCampaigns(final String username) {
    return campaignRepository.countByOwner_Username(username);
  }

}
