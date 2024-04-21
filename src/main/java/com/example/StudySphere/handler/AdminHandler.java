package com.example.StudySphere.handler;

import com.example.StudySphere.entity.User;
import com.example.StudySphere.factory.AdminFactory;
import com.example.StudySphere.factory.StudentFactory;

public class AdminHandler implements UserHandler {
    private UserHandler nextHandler;

    @Override
    public void setNextHandler(UserHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public User createUser(String type) {
        if (type.equalsIgnoreCase("Admin")) {
            return new AdminFactory().createUser();
        } else if (nextHandler != null) {
            return nextHandler.createUser(type);
        } else {
            throw new IllegalArgumentException("Invalid user type");
        }
    }
}