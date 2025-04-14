package com.example.demo.repository;

import com.example.demo.domain.Studio;
import com.example.demo.domain.YogaClass;
import com.example.demo.domain.YogaStyle;
import com.example.demo.domain.YogaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface YogaClassRepository extends JpaRepository<YogaClass, Long> {
    public List<YogaClass> findByYogaStyle(YogaStyle yogaStyle);
    @Query("SELECT u FROM YogaClass c JOIN c.users u WHERE c.id = :classId")
    List<YogaUser> findUsersByYogaClassId(@Param("classId") Long classId);
}
