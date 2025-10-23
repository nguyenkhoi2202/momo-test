package com.momo.interview.service.impl;

import com.momo.interview.entity.Bill;
import com.momo.interview.entity.Payment;
import com.momo.interview.entity.Users;
import com.momo.interview.enums.StateBillEnum;
import com.momo.interview.enums.StatePaymentEnum;
import com.momo.interview.repository.BillRepository;
import com.momo.interview.repository.PaymentRepository;
import com.momo.interview.repository.UserRepository;
import com.momo.interview.service.BillService;
import com.momo.interview.service.UserService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillServiceImpl implements BillService {

  private final BillRepository billRepository;
  private final UserRepository userRepository;
  private final PaymentRepository paymentRepository;
  private final UserService userService;

  @Override
  public void listBills() {
    System.out.println("\nLIST_BILL");
    System.out.printf("%-8s | %-10s | %-10s | %-12s | %-10s | %-20s%n",
        "Bill No.", "Type", "Amount", "Due Date", "State", "Provider");
    System.out.println("----------------------------------------------------------------------");

    billRepository.findAll().forEach(b ->
        System.out.printf("%-8d | %-10s | %-10.0f | %-12s | %-10s | %-20s%n",
            b.getId(),
            b.getType(),
            b.getAmount(),
            b.getDueDate(),
            b.getState(),
            b.getProvider())
    );
  }

  @Override
  @Transactional
  public void payBills(List<Long> ids) {
    Users u = userService.getUser();
    List<Bill> bills = billRepository.findAllById(ids).stream()
        .filter(b -> StateBillEnum.NOT_PAID.name().equals(b.getState()))
        .sorted(Comparator.comparing(Bill::getDueDate))
        .toList();

    BigDecimal total = bills.stream()
        .map(Bill::getAmount)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    if (bills.isEmpty()) {
      System.out.println("Not found a bill with such id");
      return;
    }

    if (u.getBalance().compareTo(total) < 0) {
      System.out.println("Not enough balance to proceed with payment.");
      return;
    }

    for (Bill b : bills) {
      b.setState(StateBillEnum.PAID.name());
      billRepository.save(b);
      paymentRepository.save(new Payment(b.getAmount(), LocalDate.now(),
          StatePaymentEnum.PROCESSED, b));
    }

    u.subtractFund(total);
    userRepository.save(u);
    System.out.println("Payment has been completed for Bill with id " + ids);
    System.out.println("Your current balance is: " + u.getBalance());
  }

  @Override
  public void getBillsNotPaid() {
    System.out.println("\nBILLS_NOT_PAID");
    System.out.printf("%-8s | %-10s | %-10s | %-12s | %-10s | %-20s%n",
        "Bill No.", "Type", "Amount", "Due Date", "State", "Provider");
    System.out.println("----------------------------------------------------------------------");

    billRepository.findByStateOrderByDueDate(StateBillEnum.NOT_PAID.name())
        .forEach(b -> System.out.printf("%-8d | %-10s | %-10.0f | %-12s | %-10s | %-20s%n",
            b.getId(),
            b.getType(),
            b.getAmount(),
            b.getDueDate(),
            b.getState(),
            b.getProvider()
        ));
  }

  @Override
  public Payment schedule(Long billId, LocalDate date) {
    Bill b = billRepository.findById(billId).orElse(null);
    if (b == null) {
      System.out.println("Bill not found.");
      return null;
    }
    Payment payment = new Payment(b.getAmount(), date, StatePaymentEnum.PENDING, b);
    paymentRepository.save(payment);
    System.out.println("Payment for bill id " + billId + " is scheduled on " + date);
    return payment;
  }

  @Override
  public void searchByProvider(String provider) {
    System.out.println("\nBILLS_BY_PROVIDER: " + provider.toUpperCase());
    System.out.printf("%-8s | %-10s | %-10s | %-12s | %-10s | %-20s%n",
        "Bill No.", "Type", "Amount", "Due Date", "State", "Provider");
    System.out.println("----------------------------------------------------------------------");

    billRepository.findByProviderContainingIgnoreCase(provider)
        .forEach(b -> System.out.printf("%-8d | %-10s | %-10.0f | %-12s | %-10s | %-20s%n",
            b.getId(),
            b.getType(),
            b.getAmount(),
            b.getDueDate(),
            b.getState(),
            b.getProvider()
        ));
  }
}
