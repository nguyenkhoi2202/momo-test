package com.momo.interview;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.momo.interview.entity.Bill;
import com.momo.interview.entity.Payment;
import com.momo.interview.enums.StatePaymentEnum;
import com.momo.interview.repository.PaymentRepository;
import com.momo.interview.service.BillService;
import com.momo.interview.service.impl.PaymentServiceImpl;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class PaymentServiceTest {
  private PaymentRepository paymentRepository;
  private BillService billService;
  private PaymentServiceImpl paymentService;

  @BeforeEach
  void setUp() {
    paymentRepository = mock(PaymentRepository.class);
    paymentService = new PaymentServiceImpl(paymentRepository);
  }

  @Test
  void testSchedulePayment() {
    Bill b = new Bill();
    b.setId(2L);
    b.setAmount(new BigDecimal("150000"));

    Payment p = new Payment(b.getAmount(), LocalDate.now(), StatePaymentEnum.PENDING, b);
    when(paymentRepository.save(any(Payment.class))).thenReturn(p);

    Payment result = billService.schedule(b.getId(), LocalDate.now());
    assertEquals(StatePaymentEnum.PENDING, result.getState());
    verify(paymentRepository, times(1)).save(any(Payment.class));
  }

}
