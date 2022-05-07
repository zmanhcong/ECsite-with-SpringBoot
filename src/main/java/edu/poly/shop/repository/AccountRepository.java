package edu.poly.shop.repository;

import edu.poly.shop.domain.Account;
import edu.poly.shop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByUsernameContaining(String username);
}
