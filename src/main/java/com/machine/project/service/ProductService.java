package com.machine.project.service;

import com.machine.project.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product save(Product product);

    Product update(Product product);

    Optional<Product> get(int id);

    void deleteById(int id);

    List<Product> findAll();

}
