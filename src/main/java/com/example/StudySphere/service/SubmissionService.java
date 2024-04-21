package com.example.StudySphere.service;

import com.example.StudySphere.dao.AssignmentDao;
import com.example.StudySphere.dao.SubmissionDao;
import com.example.StudySphere.entity.Assignment;
import com.example.StudySphere.entity.Material;
import com.example.StudySphere.entity.Subject;
import com.example.StudySphere.entity.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class SubmissionService extends AbstractService {

    @Autowired
    SubmissionDao submissionDao;

    @Autowired
    FileUpload fileUpload;


    @Override
    public void save(Object object) {
        if (object instanceof Submission submission) {
            submissionDao.save(submission);
        } else {
            throw new IllegalArgumentException("Object must be an instance of Result");
        }
    }

    @Override
    public void delete(Object object) {
        if (object instanceof Submission submission) {
            submissionDao.delete(submission);
        } else {
            throw new IllegalArgumentException("Object must be an instance of Result");
        }
    }

    public List<Submission> findByAssignment(int assignment_id) {
        return submissionDao.findByAssignmentId(assignment_id);
    }

//    public List<Assignment> getAllAssignmentsByTeacherId(String teacherId) {
//        return assignmentDao.findByTeacherUid(teacherId);
//    }

    public Submission findById(int id) {
        Optional<Submission> result =submissionDao.findById(id);

        Submission submission =null;

        if (result.isPresent()) {
            submission = result.get();
        }
        else {
            throw new RuntimeException("Did not find material uid - " + id);
        }

        return submission;
    }

    public  void addSubmission(Submission submission, File file) throws GeneralSecurityException, IOException {
        String fileId = fileUpload.uploadToDrive(file);
        submission.setFile_id(fileId);
        submission.setTime_of_submission(LocalDateTime.now());
        submissionDao.save(submission);

    }
}
