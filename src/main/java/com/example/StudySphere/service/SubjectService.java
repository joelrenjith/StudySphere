package com.example.StudySphere.service;

import com.example.StudySphere.dao.AssignmentDao;
import com.example.StudySphere.dao.SubjectDao;
import com.example.StudySphere.entity.*;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;

@Repository
public class SubjectService {

    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    FileUpload fileUpload;

    @Autowired
    MaterialService materialService;

    @Autowired
    AssignmentService assignmentService;
    @Autowired
    private AssignmentDao assignmentDao;


    public SubjectService(SubjectDao subjectDao) {
        this.subjectDao = subjectDao;
    }

    public List<Subject> findAll(){
        return subjectDao.findAll();
    }

    public void save(Subject subject) {
        subjectDao.save(subject);
    }
    public Subject findById(String id){
        Optional<Subject> result =subjectDao.findById(id);

        Subject subject = null;

        if (result.isPresent()) {
            subject = result.get();
        }
        else {
            throw new RuntimeException("Did not find user uid - " + id);
        }

        return subject;
    }

    public void updateSubject(Subject subject){
        Optional<Subject> result =subjectDao.findById(subject.getId());

        Subject updatedsubject = null;

        if (result.isPresent()) {
            updatedsubject = result.get();
            updatedsubject = subject;


            subjectDao.save(updatedsubject);

        }
        else {
            throw new RuntimeException("Did not find user id - " + subject.getId());
        }

    }

    public List<Subject> anchorSubjects(String id){
        return subjectDao.findByAnchorId(id);

    }

    public List<Material> findAllMaterialsBySubjectId(String id) {
        // Retrieve the subject by ID
        Subject subject = findById(id);

        System.out.println(subject.getMaterials());
            return subject.getMaterials(); // Return the list of materials associated with the subject

    }

    public void addMaterial(String subject_id,int chapter, File file) throws GeneralSecurityException, IOException {
        Subject subject = findById(subject_id);
        Material material = materialService.uploadMaterial(file,chapter);
        subject.addMaterial(material);
        subjectDao.save(subject);
    }

    public void addAssignmnet(String subject_id, Assignment assignment) throws GeneralSecurityException, IOException {
        Subject subject = findById(subject_id);
        subject.addAssignment(assignment);
        subjectDao.save(subject);
    }
    public void addQuiz(String subject_id, Quiz quiz) throws GeneralSecurityException, IOException {
        Subject subject = findById(subject_id);
        subject.addQuiz(quiz);
        subjectDao.save(subject);
    }



}
