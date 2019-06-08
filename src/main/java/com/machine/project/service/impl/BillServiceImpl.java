package com.machine.project.service.impl;

import com.machine.project.domain.Bill;
import com.machine.project.repository.BillRepository;
import com.machine.project.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillRepository billRepository;

    @Override
    public Bill save(Bill bill) {
        return billRepository.save(bill);
    }

    @Override
    public Optional<Bill> get(int id) {
        return billRepository.findById(id);
    }

    @Override
    public void deleteById(int id) {
        if (billRepository.existsById(id)) {
            billRepository.deleteById(id);
        }
    }

    public Bill update(Bill bill) {
        return billRepository.save(bill);
    }



    public List<Bill> findAll() {
        return billRepository.findAll();
    }
}
