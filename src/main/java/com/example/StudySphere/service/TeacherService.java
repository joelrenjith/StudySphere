package com.example.StudySphere.service;

import com.example.StudySphere.dao.TeacherDao;
import com.example.StudySphere.dao.UserDao;
import com.example.StudySphere.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    @Autowired
    private TeacherDao teacherDao;



    public TeacherService( TeacherDao teacherDao){
        this.teacherDao = teacherDao;

    }

    public Teacher findById(String id){
        Optional<Teacher> result =teacherDao.findById(id);

        Teacher teacher = null;

        if (result.isPresent()) {
            teacher = result.get();
        }
        else {
            throw new RuntimeException("Did not find user uid - " + id);
        }

        return teacher;
    }

    public void save(Teacher teacher){

        teacherDao.save(teacher);

    }

    public void addAssignmnet(String teacher_id, Assignment assignment) throws GeneralSecurityException, IOException {
        Teacher teacher = findById(teacher_id);
        teacher.addAssignment(assignment);
        teacherDao.save(teacher);
    }
    public void addQuiz(String teacher_id, Quiz quiz) throws GeneralSecurityException, IOException {
        Teacher teacher = findById(teacher_id);
        teacher.addQuiz(quiz);
        teacherDao.save(teacher);
    }


}
