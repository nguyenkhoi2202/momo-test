package com.momo.interview;


import com.momo.interview.service.BillService;
import com.momo.interview.service.PaymentService;
import com.momo.interview.service.UserService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
@Slf4j
public class CommandRunner implements CommandLineRunner{

  private final BillService billService;
  private final PaymentService paymentService;
  private final UserService userService;

  @Override
  public void run(String... args) throws Exception {
    Scanner sc = new Scanner(System.in);
    System.out.println("BillPay CLI started. Type HELP for commands.");
    while (true) {
      System.out.println("> ");
      String[] input = sc.nextLine().split(" ");
      if (input.length == 0) {
        continue;
      }

      switch (input[0].toUpperCase()) {
        case "EXIT" -> userService.goodBye();
        case "CASH_IN" -> userService.cashIn(new BigDecimal(input[1]));
        case "LIST_BILL" -> billService.listBills();
        case "PAY" -> {
          List<Long> ids = Arrays.stream(Arrays.copyOfRange(input, 1, input.length))
              .map(Long::parseLong).toList();
          billService.payBills(ids);
        }
        case "DUE_DATE" -> billService.getBillsNotPaid();
        case "SCHEDULE 2 28/10/2020" -> billService.schedule(Long.parseLong(input[1]),
            LocalDate.parse(input[2], DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        case "LIST_PAYMENT" -> paymentService.listPayments();
        case "SEARCH_BILL_BY_PROVIDER" -> billService.searchByProvider(input[1]);
        default -> System.out.println("Error choose");
      }
    }
  }
}
