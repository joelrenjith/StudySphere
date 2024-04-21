package com.example.StudySphere.factory;

import com.example.StudySphere.entity.User;
import com.example.StudySphere.handler.AdminHandler;
import com.example.StudySphere.handler.StudentHandler;
import com.example.StudySphere.handler.TeacherHandler;
import com.example.StudySphere.handler.UserHandler;

public class UserFactoryManager {
    private UserHandler handlerChain;

    public UserFactoryManager() {
        // Create the chain of responsibility
        UserHandler studentHandler = new StudentHandler();
        UserHandler teacherHandler = new TeacherHandler();
        UserHandler adminHandler = new AdminHandler();

        studentHandler.setNextHandler(teacherHandler);
        teacherHandler.setNextHandler(adminHandler);

        this.handlerChain = studentHandler;
    }

    public User createUser(String type) {
        return handlerChain.createUser(type);
    }
}
