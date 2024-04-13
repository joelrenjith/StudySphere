package com.example.StudySphere.service;

import com.example.StudySphere.dao.MaterialDao;
import com.example.StudySphere.entity.Material;
import com.example.StudySphere.entity.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MaterialService {

    @Autowired
    MaterialDao materialDao;

    public Material findMaterialById(int id) {
        Optional<Material> result =materialDao.findById(id);

        Material material=null;

        if (result.isPresent()) {
            material = result.get();
        }
        else {
            throw new RuntimeException("Did not find material uid - " + id);
        }

        return material;
    }
}
