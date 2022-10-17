package com.example.ShowMakerCode.Repository;

import com.example.ShowMakerCode.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query(value = "select o from Account o where o.username = ?1 and o.password = ?2",nativeQuery = false)
    Account findByUsernameandPass(String username , String password);


}