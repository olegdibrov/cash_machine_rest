package com.machine.project.service.impl;

import com.machine.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.machine.project.domain.Product;
import com.machine.project.repository.ProductRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteById(int id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);

        }
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> get(int id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
