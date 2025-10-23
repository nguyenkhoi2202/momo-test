package com.momo.interview.service;

import com.momo.interview.entity.Users;
import java.math.BigDecimal;

public interface UserService {
  Users getUser();
  void cashIn(BigDecimal amount);

  void goodBye();
}
