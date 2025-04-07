package com.example.demo.repository;

import com.example.demo.domain.Studio;
import com.example.demo.domain.YogaClass;
import com.example.demo.domain.YogaStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface YogaClassRepository extends JpaRepository<YogaClass, Long> {
    public List<YogaClass> findByYogaStyle(YogaStyle yogaStyle);
}
