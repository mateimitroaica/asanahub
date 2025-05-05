package com.example.demo.repository;

import com.example.demo.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface YogaClassRepository extends JpaRepository<YogaClass, Long> {
    @Query("SELECT u FROM YogaClass c JOIN c.users u WHERE c.id = :classId")
    List<YogaUser> findUsersByYogaClassId(@Param("classId") Long classId);

//    List<YogaClass> findYogaClassByNameContainingIgnoreCase(String name);
//    List<YogaClass> findByYogaStyle_ClassType(YogaClassType classType);
//
//    @Query("SELECT y FROM YogaClass y WHERE DATE(y.timeAndDate) = :date")
//    List<YogaClass> findByDate(@Param("date") LocalDate date);
//
//    @Query("SELECT y FROM YogaClass y WHERE y.yogaStyle.classType = :style AND DATE(y.timeAndDate) = :date")
//    List<YogaClass> findByYogaStyle_ClassTypeAndDate(@Param("style") YogaClassType style,
//                                                     @Param("date") LocalDate date);
Page<YogaClass> findAll(Pageable pageable);

    Page<YogaClass> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT y FROM YogaClass y WHERE DATE(y.timeAndDate) = :date")
    Page<YogaClass> findByDate(@Param("date") LocalDate date, Pageable pageable);

    @Query("SELECT y FROM YogaClass y WHERE y.yogaStyle.classType = :style AND DATE(y.timeAndDate) = :date")
    Page<YogaClass> findByYogaStyle_ClassTypeAndDate(@Param("style") YogaClassType style,
                                                     @Param("date") LocalDate date,
                                                     Pageable pageable);

    Page<YogaClass> findByYogaStyle_ClassType(YogaClassType classType, Pageable pageable);
}
