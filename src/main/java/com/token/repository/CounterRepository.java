package com.token.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.token.entity.Counter;

@Repository
public interface CounterRepository extends JpaRepository<Counter, Long>, JpaSpecificationExecutor<Counter> {

}