package com.benrkia.datagenerator;

import com.benrkia.datagenerator.entity.Campaign;
import com.benrkia.datagenerator.entity.User;
import com.benrkia.datagenerator.service.CampaignService;
import com.benrkia.datagenerator.service.Generator;
import com.benrkia.datagenerator.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

@SpringBootApplication
@RequiredArgsConstructor
public class DataGeneratorApplication implements CommandLineRunner {
  private final UserService userService;
  private final CampaignService campaignService;
  private static final int campaignUserFactor = 2;
  private static final int campaignGenerationFrequencyInSeconds = 10;
  private static final int userGenerationFrequencyInSeconds =  campaignGenerationFrequencyInSeconds * (campaignUserFactor * 2);

  public static void main(String[] args) {
    SpringApplication.run(DataGeneratorApplication.class, args);
  }

  @Override
  public void run(final String... args) throws Exception {
    Flux.interval(Duration.ZERO, Duration.ofSeconds(userGenerationFrequencyInSeconds))
        .map(i -> Generator.generateFakeUser())
				.map(userService::saveUser)
        .flatMap(this::generateCampaigns)
        .subscribe();
  }

  public  Flux<Campaign> generateCampaigns(User user) {
    return Flux.interval(Duration.ofSeconds(campaignGenerationFrequencyInSeconds))
        .take(campaignUserFactor)
        .map(i -> Generator.generateFakeCampaign(user))
        .map(campaignService::saveCampaign);
  }
}
