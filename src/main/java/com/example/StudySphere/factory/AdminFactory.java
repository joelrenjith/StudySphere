package com.example.StudySphere.factory;

import com.example.StudySphere.entity.User;

public class AdminFactory implements UserFactory {
    @Override
    public User createUser() {
        User admin = new User();
        admin.setRole("ROLE_ADMIN");
        return admin;
    }
}
