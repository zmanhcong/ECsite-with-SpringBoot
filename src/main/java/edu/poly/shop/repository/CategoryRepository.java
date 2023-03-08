package edu.poly.shop.repository;
import edu.poly.shop.domain.Category;
import edu.poly.shop.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByNameContaining(String name);

    @Query(value = "SELECT * FROM categories WHERE name LIKE %:keyword%", countQuery = "SELECT COUNT(*) FROM categories WHERE name LIKE %:keyword%", nativeQuery = true)
    Page<Category> findByNameContaining1(@Param("keyword") String keyword, Pageable pageable);


    @Query(value = "SELECT * FROM categories WHERE name LIKE %:keyword%", nativeQuery = true)
    public List<Category> findProductName_nativeQuery(String keyword);
}
