package com.momo.interview;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.momo.interview.entity.Users;
import com.momo.interview.repository.UserRepository;

import com.momo.interview.service.impl.UserServiceImpl;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserServiceTest {
  private UserRepository userRepository;
  private UserServiceImpl userService;

  @BeforeEach
  void setUp() {
    userRepository = mock(UserRepository.class);
    userService = new UserServiceImpl(userRepository);
  }

  @Test
  void testGetCustomerSuccess() {
    Users u = new Users();
    u.setId(1L);
    u.setBalance(BigDecimal.ZERO);

    when(userRepository.findById(1L)).thenReturn(Optional.of(u));

    Users result = userService.getUser();
    assertNotNull(result);
    assertEquals(1L, result.getId());
  }

  @Test
  void testGetCustomerNotFound() {
    when(userRepository.findById(1L)).thenReturn(Optional.empty());
    assertThrows(RuntimeException.class, () -> userService.getUser());
  }

  @Test
  void testCashInIncreasesBalance() {
    Users u = new Users();
    u.setId(1L);
    u.setBalance(new BigDecimal("1000000"));
    when(userRepository.findById(1L)).thenReturn(Optional.of(u));

    userService.cashIn(new BigDecimal("50000"));
    assertEquals(new BigDecimal("150000"), u.getBalance());
  }
}
