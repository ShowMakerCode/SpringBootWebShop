package com.example.ShowMakerCode.Service.ServiceIplm;

import com.example.ShowMakerCode.Entity.Order;
import com.example.ShowMakerCode.Repository.OrderRepository;
import com.example.ShowMakerCode.Service.EntiryService.OrderService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
@Service
public class OrderServiceiplm implements OrderService {
    OrderRepository orderRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public List<Order> findAll(Sort sort) {
        return orderRepository.findAll(sort);
    }

    public <S extends Order> S save(S entity) {
        return orderRepository.save(entity);
    }

    public Optional<Order> findById(Long aLong) {
        return orderRepository.findById(aLong);
    }

    public void deleteById(Long aLong) {
        orderRepository.deleteById(aLong);
    }

    public <S extends Order, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return orderRepository.findBy(example, queryFunction);
    }
}
