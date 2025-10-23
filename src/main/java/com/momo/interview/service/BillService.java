package com.momo.interview.service;

import com.momo.interview.entity.Payment;
import java.time.LocalDate;
import java.util.List;

public interface BillService {
  void listBills();

  void payBills(List<Long> ids);

  void getBillsNotPaid();

  Payment schedule(Long billId, LocalDate date);
  void searchByProvider(String provider);

}
