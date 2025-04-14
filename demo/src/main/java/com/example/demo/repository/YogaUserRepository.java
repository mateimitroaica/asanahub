package com.example.demo.repository;

import com.example.demo.domain.YogaClass;
import com.example.demo.domain.YogaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YogaUserRepository extends JpaRepository<YogaUser, Long> {
    @Query("SELECT u FROM YogaUser u JOIN u.reservations c WHERE c.id = :classId")
    List<YogaUser> findAllByYogaClassId(@Param("classId") Long classId);
}
