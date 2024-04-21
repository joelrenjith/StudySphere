package com.example.StudySphere.dao;

import com.example.StudySphere.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionDao extends JpaRepository<Submission, Integer> {
    Submission findByStudentUidAndAssignmentId(String studentId, int assignmentId);
    List<Submission> findByAssignmentId(int assignmentId);

}
