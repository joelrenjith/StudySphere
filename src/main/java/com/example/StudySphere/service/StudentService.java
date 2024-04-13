package com.example.StudySphere.service;

import com.example.StudySphere.dao.StudentDao;
import com.example.StudySphere.dao.TeacherDao;
import com.example.StudySphere.dao.UserDao;
import com.example.StudySphere.entity.Student;
import com.example.StudySphere.entity.Teacher;
import com.example.StudySphere.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
