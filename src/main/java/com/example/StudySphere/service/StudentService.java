package com.example.StudySphere.service;

import com.example.StudySphere.dao.StudentDao;
import com.example.StudySphere.dao.TeacherDao;
import com.example.StudySphere.dao.UserDao;
import com.example.StudySphere.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentDao studentDao;


    public StudentService( StudentDao studentDao){
        this.studentDao = studentDao;

    }

    public void save(Student student){

        studentDao.save(student);

    }

    public Student findById(String id){
        Optional<Student> result =studentDao.findById(id);

        Student student = null;

        if (result.isPresent()) {
            student = result.get();
        }
        else {
            throw new RuntimeException("Did not find user uid - " + id);
        }

        return student;
    }

    public void addSubmission(String student_id, Submission submission) throws GeneralSecurityException, IOException {
        Student student = findById(student_id);
        student.addSubmission(submission);
        studentDao.save(student);
    }
}
