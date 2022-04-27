package com.benrkia.datagenerator.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Campaign {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private CampaignType type;

  @ManyToOne
  @JoinColumn(name = "owner_id", nullable = false)
  private User owner;

  @Override
  public String toString() {
    return "Campaign{" +
        "name='" + name + '\'' +
        ", type=" + type +
        '}';
  }
}
