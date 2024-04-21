package com.example.StudySphere.service;

import com.example.StudySphere.dao.TeacherDao;
import com.example.StudySphere.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

@Service
public class TeacherService extends AbstractService {

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

    @Override
    public void save(Object object) {
        if (object instanceof Teacher teacher) {
            teacherDao.save(teacher);
        } else {
            throw new IllegalArgumentException("Object must be an instance of Teacher");
        }
    }

    @Override
    public void delete(Object object) {
        if (object instanceof Teacher teacher) {
            teacherDao.delete(teacher);
        } else {
            throw new IllegalArgumentException("Object must be an instance of Teacher");
        }
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
