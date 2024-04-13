package com.example.StudySphere.service;

import com.example.StudySphere.dao.StudentDao;
import com.example.StudySphere.dao.TeacherDao;
import com.example.StudySphere.dao.UserDao;
import com.example.StudySphere.entity.Student;
import com.example.StudySphere.entity.Teacher;
import com.example.StudySphere.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;




    public UserService( UserDao userDao){
        this.userDao = userDao;

    }
    public User findById(String uid){
        Optional<User> result =userDao.findById(uid);

        User user = null;

        if (result.isPresent()) {
            user = result.get();
        }
        else {
            throw new RuntimeException("Did not find user uid - " + uid);
        }

        return user;
    }

    public void updateProfile(User user){
        Optional<User> result =userDao.findById(user.getUid());

        User updateduser = null;

        if (result.isPresent()) {
            updateduser = result.get();
            updateduser.setUsername(user.getUsername());
            System.out.println("new password is :"+user.getPassword());
            System.out.println("length of new password is"+user.getPassword().length());
            if(!user.getPassword().isEmpty()){
                String passwd = "{noop}"+user.getPassword();
                updateduser.setPassword(passwd);
            }
            userDao.save(updateduser);

        }
        else {
            throw new RuntimeException("Did not find user id - " + user.getUid());
        }

    }

    public void save(User user) {
        String passwd = "{noop}" + user.getPassword();
        user.setPassword(passwd);
        user.setActive(1);
        userDao.save(user);

        switch (user.getRole()) {
            case "ROLE_STUDENT":
                if (user instanceof Student) {
                    studentService.save((Student) user);
                } else {
                    throw new IllegalArgumentException("User is not an instance of Student");
                }
                break;
            case "ROLE_TEACHER":
                if (user instanceof Teacher) {
                    teacherService.save((Teacher) user);
                } else {
                    throw new IllegalArgumentException("User is not an instance of Teacher");
                }
                break;
            case "ROLE_ADMIN":
                // Handle admin-specific logic if needed
                break;
            default:
                throw new IllegalArgumentException("Unsupported user role: " + user.getRole());
        }
    }



}
