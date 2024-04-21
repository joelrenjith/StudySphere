package com.example.StudySphere.service;

import org.springframework.stereotype.Service;

@Service
abstract class AbstractService {


    abstract void save(Object object);
    abstract void delete(Object object);
}
