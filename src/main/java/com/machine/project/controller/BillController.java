package com.machine.project.controller;

import com.machine.project.domain.Bill;
import com.machine.project.domain.Payment;
import com.machine.project.domain.User;
import com.machine.project.service.BillService;
import com.machine.project.service.PaymentService;
import com.machine.project.service.ProductService;
import com.machine.project.service.UserService;
import com.machine.project.utils.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.machine.project.domain.Product;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

@Api
@RestController
public class BillController {

    @Autowired
    private BillService billService;

    @Autowired
    private ProductService productService;


    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;


    @PostMapping(value = "/bills")
    public ResponseEntity<List<Product>> createNewBill(@AuthenticationPrincipal User user) {
        Bill bill = Bill.builder()
                .date(LocalDate.now().toString())
                .user(userService.findById(user.getId()))
                .billStatus(Status.CREATED)
                .build();
        billService.save(bill);
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/bills")
    public ResponseEntity<Collection<Bill>> openBillPage() {
        return new ResponseEntity<>(billService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/bills/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable Integer id) {
        return billService.get(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/bills/{id}/products")
    public ResponseEntity<List<Payment>> getPaymentsInBill(@PathVariable("id") Integer idBill) {
        Optional<Bill> bill = billService.get(idBill);
        List<Payment> payments = bill.isPresent() ? paymentService.findPaymentsByBill(bill.get().getIdBill()) : new ArrayList<>();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @PostMapping(value = "bills/{id}/products" )
    public ResponseEntity<Payment> addProductToBill(@PathVariable("id") Integer idBill,
                                                    @RequestBody Product product, @RequestParam Integer quantity) {
        Optional<Bill> bill = billService.get(idBill);
        Optional<Payment> payment = bill.flatMap(bill1 -> Optional.of(Payment.builder()
                .bill(bill1)
                .product(product)
                .quantity(quantity)
                .build()));
        return payment
                .map(payment1 -> new ResponseEntity<>(paymentService.addProductToBill(payment1), HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }


    @ApiOperation(value = "delete")
    @DeleteMapping("/bills/{id}/products/{idpr}")
    public ResponseEntity<List<Payment>> deleteProductFromBill(@PathVariable("id") Integer id,
                                                               @PathVariable("idpr") Integer idpr) {
        Optional<Bill> bill = billService.get(id);
        Optional<Payment> payment = paymentService.get(idpr);

        if (bill.isPresent() && bill.get() == payment.get().getBill()) {
            paymentService.deleteProductFromBill(payment.get());
            return new ResponseEntity<>(paymentService.findPaymentsByBill(bill.get().getIdBill()), HttpStatus.OK);
        } else
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "close")
    @PutMapping("/bills/{id}")
    public ResponseEntity<Bill> closeBill(@PathVariable("id") Integer idBill) {
        Optional<Bill> bill = billService.get(idBill);
        if (bill.isPresent()) {
            Bill updateBill = bill.get();
            updateBill.setBillStatus(Status.FINISHED);
            return ResponseEntity.ok().body(billService.update(updateBill));
        } else return ResponseEntity.notFound().build();
    }
}


