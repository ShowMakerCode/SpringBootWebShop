package com.example.ShowMakerCode.Service.EntiryService;

import com.example.ShowMakerCode.Entity.OrderDetail;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface OrderDetailService {
    List<OrderDetail> findAll();

    List<OrderDetail> findAll(Sort sort);

    List<OrderDetail> findAllById(Iterable<Long> longs);

    <S extends OrderDetail> S save(S entity);

    Optional<OrderDetail> findById(Long aLong);

    void deleteById(Long aLong);

    <S extends OrderDetail> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends OrderDetail, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);
}
