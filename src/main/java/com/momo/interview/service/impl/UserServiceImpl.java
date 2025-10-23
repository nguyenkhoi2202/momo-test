package com.momo.interview.service.impl;

import com.momo.interview.entity.Users;
import com.momo.interview.service.UserService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  @Override
  public Users getCustomer() {
    return null;
  }

  @Override
  public void cashIn(BigDecimal amount) {

  }
}
