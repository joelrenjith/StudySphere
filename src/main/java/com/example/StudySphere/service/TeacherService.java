package com.example.StudySphere.service;

import com.example.StudySphere.dao.TeacherDao;
import com.example.StudySphere.dao.UserDao;
import com.example.StudySphere.entity.Subject;
import com.example.StudySphere.entity.Teacher;
import com.example.StudySphere.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
