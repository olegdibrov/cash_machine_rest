package com.machine.project.service;


import com.machine.project.domain.Bill;

import java.util.List;
import java.util.Optional;

public interface BillService {

    Bill save(Bill bill);

    Bill update(Bill bill);

    Optional<Bill> get(int id);

    void deleteById(int id);

    List<Bill> findAll();

}
