package com.example.demo.repository;

import com.example.demo.domain.YogaClassType;
import com.example.demo.domain.YogaStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface YogaStyleRepository extends JpaRepository<YogaStyle, Long> {
}
