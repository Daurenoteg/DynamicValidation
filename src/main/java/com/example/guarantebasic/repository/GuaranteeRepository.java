package com.example.guarantebasic.repository;

import com.example.guarantebasic.model.Guarantee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GuaranteeRepository extends JpaRepository<Guarantee, Long> {
    Optional<Object> findById(UUID id);
}
