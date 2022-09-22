package com.swp.hr_backend.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "candidate")
public class Candidate extends Account {
  @Id
  private String account_id;
  @OneToOne(mappedBy = "candidate", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private Account account;
}
