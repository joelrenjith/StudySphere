package com.example.StudySphere.factory;

import com.example.StudySphere.entity.Student;
import com.example.StudySphere.entity.User;

public class StudentFactory implements UserFactory {
    @Override
    public User createUser() {
        Student student = new Student();
        student.setRole("ROLE_STUDENT");
        return student;
    }
}
