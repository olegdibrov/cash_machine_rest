package com.machine.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.machine.project.utils.Status;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@ToString
@Table(name = "bill")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Bill {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idBill;


    //ToDO change to SQLData
    private String date;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    

    @Enumerated(EnumType.STRING)
    private Status billStatus;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Payment> payments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill = (Bill) o;
        return Objects.equals(idBill, bill.idBill) &&
                Objects.equals(date, bill.date) &&
                Objects.equals(user, bill.user) &&
                billStatus == bill.billStatus &&
                Objects.equals(payments, bill.payments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBill, date, user, billStatus, payments);
    }
}
