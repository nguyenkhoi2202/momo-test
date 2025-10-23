package com.momo.interview.service.impl;

import com.momo.interview.entity.Users;
import com.momo.interview.repository.UserRepository;
import com.momo.interview.service.UserService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public Users getUser() {
    System.out.println("\nGET_CUSTOMER");
    return userRepository.findAll().stream().findFirst()
        .orElseThrow(() -> new RuntimeException("User not found"));
  }

  @Override
  public void cashIn(BigDecimal amount) {
    Users u = getUser();
    u.addBalance(amount);
    userRepository.save(u);
    System.out.println("Your available balance: " + u.getBalance());
  }

  @Override
  public void goodBye() {
    System.exit(0);
    System.out.println("Good bye!");
  }
}
