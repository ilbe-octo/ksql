package com.benrkia.datagenerator.service;

import com.benrkia.datagenerator.entity.User;
import com.benrkia.datagenerator.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final CampaignService campaignService;

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public User saveUser(final User user) {
    log.info("Saving new user {}", user);
    return userRepository.save(user);
  }

  public long countAllUsers() {
    return userRepository.count();
  }

  public long countUserCampaigns(final String username) {
    if (!userRepository.existsByUsername(username))
      return 0;

    return campaignService.countUserCampaigns(username);
  }
}
