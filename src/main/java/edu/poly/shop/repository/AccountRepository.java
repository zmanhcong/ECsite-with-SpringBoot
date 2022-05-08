package edu.poly.shop.repository;

import edu.poly.shop.domain.Account;
import edu.poly.shop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByUsernameContaining(String username);

    Optional<Account> findByUsername(String username);
}
