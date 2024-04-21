package com.example.StudySphere.factory;

import com.example.StudySphere.entity.Teacher;
import com.example.StudySphere.entity.User;

public class TeacherFactory implements UserFactory {
    @Override
    public User createUser() {
        Teacher teacher = new Teacher();
        teacher.setRole("ROLE_TEACHER");
        return teacher;
    }
}
