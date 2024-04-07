package com.example.StudySphere.service;

import com.example.StudySphere.dao.UserDao;
import com.example.StudySphere.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserDao userDao;

    @Autowired
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

}
