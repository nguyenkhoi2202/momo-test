package com.momo.interview.service;

import com.momo.interview.entity.Users;
import java.math.BigDecimal;

public interface UserService {
  Users getCustomer();
  void cashIn(BigDecimal amount);
}
