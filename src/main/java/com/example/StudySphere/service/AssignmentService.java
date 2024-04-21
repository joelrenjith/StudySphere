package com.example.StudySphere.service;

import com.example.StudySphere.dao.AssignmentDao;
import com.example.StudySphere.entity.Assignment;
import com.example.StudySphere.entity.Material;
import com.example.StudySphere.entity.Student;
import com.example.StudySphere.entity.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {

    @Autowired
    AssignmentDao assignmentDao;

    @Autowired
    FileUpload fileUpload;

    public List<Assignment> getAllAssignments() {return assignmentDao.findAll();}

    public List<Assignment> getAllAssignmentsByTeacherId(String teacherId) {
            return assignmentDao.findByTeacherUid(teacherId);
    }

    public  Assignment addAssignment(Assignment assignment, File file) throws GeneralSecurityException, IOException {
        String fileId = fileUpload.uploadToDrive(file);
        assignment.setFile_id(fileId);
        assignmentDao.save(assignment);
        return assignment;

    }

    public Assignment findById(int id) {
        Optional<Assignment> result =assignmentDao.findById(id);

        Assignment assignment=null;

        if (result.isPresent()) {
            assignment = result.get();
        }
        else {
            throw new RuntimeException("Did not find material uid - " + id);
        }

        return assignment;
    }

    public void addSubmission(int assignment_id, Submission submission) throws GeneralSecurityException, IOException {
        Assignment assignment = findById(assignment_id);
        assignment.addSubmission(submission);
        assignmentDao.save(assignment);
    }
}
