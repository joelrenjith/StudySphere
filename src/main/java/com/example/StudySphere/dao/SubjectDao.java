package com.example.StudySphere.dao;

import com.example.StudySphere.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectDao extends JpaRepository<Subject, String> {

    List<Subject> findByAnchorId(String anchorId);
}
