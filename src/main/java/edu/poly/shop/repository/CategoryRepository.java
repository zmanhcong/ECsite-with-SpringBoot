package edu.poly.shop.repository;
import edu.poly.shop.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
      List<Category> findByNameContaining(String name);
   }
