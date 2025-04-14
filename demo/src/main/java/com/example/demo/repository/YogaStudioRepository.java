package com.example.demo.repository;

import com.example.demo.domain.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface YogaStudioRepository extends JpaRepository<Studio, Long> {
    Optional<Studio> findByName(String name);
}
