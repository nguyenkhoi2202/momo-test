package com.momo.interview.repository;

import com.momo.interview.entity.Bill;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
  List<Bill> findByProviderContainingIgnoreCase(String provider);
  List<Bill> findByStateOrderByDueDate(String state);
}
