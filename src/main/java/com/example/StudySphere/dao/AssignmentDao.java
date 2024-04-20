package com.example.StudySphere.dao;

import com.example.StudySphere.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentDao extends JpaRepository<Assignment,Integer> {

    List<Assignment> findByTeacherUid(String teacher_id);
}
