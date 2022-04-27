package com.benrkia.datagenerator.api;

import com.benrkia.datagenerator.entity.User;
import com.benrkia.datagenerator.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping
  public ResponseEntity<List<User>> users() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  @GetMapping("/count")
  public ResponseEntity<Long> usersCount() {
    var usersCount = userService.countAllUsers();
    return ResponseEntity.ok(usersCount);
  }

  @GetMapping("/{username}/count")
  public ResponseEntity<Long> usersCount(@PathVariable("username") final String username) {
    var campaignsCount = userService.countUserCampaigns(username);
    return ResponseEntity.ok(campaignsCount);
  }

}
