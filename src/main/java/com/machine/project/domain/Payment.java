package com.machine.project.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@ToString
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idPayment;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    @Transient
    private Currency currency;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(idPayment, payment.idPayment) &&
                Objects.equals(bill, payment.bill) &&
                Objects.equals(product, payment.product) &&
                Objects.equals(quantity, payment.quantity) &&
                Objects.equals(currency, payment.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPayment, bill, product, quantity, currency);
    }
}

