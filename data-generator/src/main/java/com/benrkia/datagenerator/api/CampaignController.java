package com.benrkia.datagenerator.api;

import com.benrkia.datagenerator.service.CampaignService;
import com.benrkia.datagenerator.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/campaigns")
@RequiredArgsConstructor
public class CampaignController {
  private final CampaignService campaignService;

  @GetMapping("/count")
  public ResponseEntity<Long> campaignsCount() {
    var campaignsCount = campaignService.countAllCampaigns();
    return ResponseEntity.ok(campaignsCount);
  }

}
