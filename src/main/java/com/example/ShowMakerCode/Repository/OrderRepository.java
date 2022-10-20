package com.example.ShowMakerCode.Repository;

import com.example.ShowMakerCode.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "select o from Order o where o.createBy like ?1",nativeQuery = false)
    Order findByCreater(String username);
}