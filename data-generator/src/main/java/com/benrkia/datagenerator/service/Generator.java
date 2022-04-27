package com.benrkia.datagenerator.service;

import com.benrkia.datagenerator.entity.Campaign;
import com.benrkia.datagenerator.entity.CampaignType;
import com.benrkia.datagenerator.entity.User;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class Generator {

  public static User generateFakeUser() {
    log.info("generating new user");
    var faker = new Faker();
    return User.builder()
        .username(faker.name().username())
        .build();
  }

  public static Campaign generateFakeCampaign(final User user) {
    log.info("generating new campaign for user {}", user);
    var faker = new Faker();
    var random = new Random();
    int idx = random.nextInt(CampaignType.values().length);
    return Campaign.builder()
        .owner(user)
        .type(CampaignType.values()[idx])
        .name(faker.funnyName().name())
        .build();
  }
}
