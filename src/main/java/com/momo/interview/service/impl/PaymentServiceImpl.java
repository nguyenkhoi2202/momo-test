package com.momo.interview.service.impl;

import com.momo.interview.repository.PaymentRepository;
import com.momo.interview.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
  private final PaymentRepository paymentRepository;

  @Override
  public void listPayments() {
    System.out.println("\nLIST_PAYMENT");
    System.out.printf("%-8s | %-10s | %-15s | %-12s | %-10s%n",
        "Bill No.", "Amount", "Payment Date", "State", "Bill ID");
    System.out.println("-------------------------------------------------------------");

    paymentRepository.findAllByOrderByPaymentDate()
        .forEach(p -> System.out.printf("%-8d | %-10.0f | %-15s | %-12s | %-10d%n",
            p.getId(),
            p.getAmount(),
            p.getPaymentDate(),
            p.getState(),
            p.getBill().getId()
        ));
  }
}
