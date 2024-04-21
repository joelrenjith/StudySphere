package com.example.StudySphere.handler;

import com.example.StudySphere.entity.User;

public interface UserHandler {
    User createUser(String type);
    void setNextHandler(UserHandler nextHandler);
}
