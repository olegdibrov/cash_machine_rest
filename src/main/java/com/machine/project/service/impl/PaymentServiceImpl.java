package com.machine.project.service.impl;

import com.machine.project.domain.Bill;
import com.machine.project.domain.Payment;
import com.machine.project.domain.Product;
import com.machine.project.repository.BillRepository;
import com.machine.project.repository.ProductRepository;
import com.machine.project.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.machine.project.repository.PaymentRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BillRepository billRepository;


    @Override
    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public void deleteById(int id) {
        if (paymentRepository.existsById(id)) {
            paymentRepository.deleteById(id);

        }
    }

    @Override
    public Payment update(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Optional<Payment> get(int id) {
        return paymentRepository.findById(id);
    }

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Transactional
    @Override
    public Payment addProductToBill(Payment payment) {
        Optional.of(payment).ifPresent(payment1 -> paymentRepository.save(payment1));
        Optional<Product> optionalProduct = productRepository.findById(payment.getProduct().getId());
        optionalProduct.ifPresent(product -> {
            product.setQuantity(product.getQuantity() - payment.getQuantity());
            productRepository.save(product);
        });
        return payment;
    }

    @Override
    public List<Payment> findPaymentsByBill(Integer idBill) {
        Optional<Bill> optionalBill = billRepository.findById(idBill);
        List<Payment> payments = optionalBill.isPresent() ? paymentRepository.findAllByBill(optionalBill.get())
                : new ArrayList<>();
        return payments;
    }

    @Override
    @Transactional
    public Payment deleteProductFromBill(Payment payment) {
        paymentRepository.delete(payment);
        Optional<Product> optionalProduct = productRepository.findById(payment.getProduct().getId());
        optionalProduct.ifPresent(product1 -> {
            product1.setQuantity(product1.getQuantity() + payment.getQuantity());
            productRepository.save(product1);
        });
        return payment;
    }
}
