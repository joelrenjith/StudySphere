package com.example.StudySphere.handler;

import com.example.StudySphere.entity.User;
import com.example.StudySphere.factory.TeacherFactory;

public class TeacherHandler implements UserHandler {
    private UserHandler nextHandler;

    @Override
    public void setNextHandler(UserHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public User createUser(String type) {
        if (type.equalsIgnoreCase("Teacher")) {
            return new TeacherFactory().createUser();
        } else if (nextHandler != null) {
            return nextHandler.createUser(type);
        } else {
            throw new IllegalArgumentException("Invalid user type");
        }
    }
}