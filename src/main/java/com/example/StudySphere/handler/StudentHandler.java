package com.example.StudySphere.handler;

import com.example.StudySphere.entity.User;
import com.example.StudySphere.factory.StudentFactory;

public class StudentHandler implements UserHandler {
    private UserHandler nextHandler;

    @Override
    public void setNextHandler(UserHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public User createUser(String type) {
        if (type.equalsIgnoreCase("Student")) {
            return new StudentFactory().createUser();
        } else if (nextHandler != null) {
            return nextHandler.createUser(type);
        } else {
            throw new IllegalArgumentException("Invalid user type");
        }
    }
}

