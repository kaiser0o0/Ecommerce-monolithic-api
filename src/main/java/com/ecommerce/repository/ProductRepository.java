package com.ecommerce.repository;

import com.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Pagination kullanımı
    Page<Product> findAllByIsDeletedFalse(Pageable pageable);
    Page<Product> findByProducerIdAndIsDeletedFalse(Long producerId, Pageable pageable);
}