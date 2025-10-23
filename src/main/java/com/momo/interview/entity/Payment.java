package com.momo.interview.entity;

import com.momo.interview.enums.StatePaymentEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private BigDecimal amount;

  private LocalDate paymentDate;

  private String state;

  @ManyToOne
  private Bill bill;

  public Payment(BigDecimal amount, LocalDate now, StatePaymentEnum statePaymentEnum, Bill bill) {
    this.amount = amount;
    this.paymentDate = now;
    this.state = statePaymentEnum.name();
    this.bill = bill;
  }
}
