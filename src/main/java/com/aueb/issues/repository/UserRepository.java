package com.aueb.issues.repository;

import com.aueb.issues.model.entity.UserEntity;
import com.aueb.issues.model.enums.IssueType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public Optional<UserEntity> findByEmail(String email);
    public Optional<UserEntity> findById(String id);
    @Query(value = "select u from UserEntity as u "+
    "where (:issueType = null or u.technicalTeam = :issueType)"+
    "and (u.role='TECHNICIAN')")
    public List<UserEntity> findByTechTeam(@Param("issueType") IssueType issueType);
    @Query(value ="select u from UserEntity as u "+
            "where u.role='TEACHER'" )
    public List<UserEntity> findTeachers();

    Optional<List<UserEntity>> findByIdIn(List<String> userIds);
}
