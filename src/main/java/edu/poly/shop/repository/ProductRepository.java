package edu.poly.shop.repository;

import edu.poly.shop.domain.Category;
import edu.poly.shop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContaining(String name);

    @Query(value = "SELECT * FROM products p WHERE p.name LIKE %:keyword%", nativeQuery = true)
    List<Product> findProductName_nativeQuery(String keyword);
}