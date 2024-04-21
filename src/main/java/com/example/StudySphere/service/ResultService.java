package com.example.StudySphere.service;

import com.example.StudySphere.dao.ResultDao;
import com.example.StudySphere.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ResultService {

    @Autowired
    ResultDao resultDao;

    public void save(Result result){resultDao.save(result);}
    public void delete(Result result){resultDao.delete(result);}
    public void deleteResultById(int resultId) {
        resultDao.deleteById(resultId);
    }
}
