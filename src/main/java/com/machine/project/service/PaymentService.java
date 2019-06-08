package com.machine.project.service;

import com.machine.project.domain.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    Payment save(Payment payment);

    Payment update(Payment payment);

    Optional<Payment> get(int id);

    void deleteById(int id);

    List<Payment> findAll();

    Payment addProductToBill(Payment payment);

    List<Payment> findPaymentsByBill(Integer idBill);

    Payment deleteProductFromBill(Payment payment);
}
