package com.example.StudySphere.service;

import com.example.StudySphere.dao.ResultDao;
import com.example.StudySphere.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ResultService extends AbstractService {

    @Autowired
    ResultDao resultDao;



    @Override
    public void save(Object object) {
        if (object instanceof Result result) {
            resultDao.save(result);
        } else {
            throw new IllegalArgumentException("Object must be an instance of Result");
        }
    }

    @Override
    public void delete(Object object) {
        if (object instanceof Result result) {
            resultDao.delete(result);
        } else {
            throw new IllegalArgumentException("Object must be an instance of Result");
        }
    }
}
