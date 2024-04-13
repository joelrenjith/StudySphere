package com.example.StudySphere.dao;

import com.example.StudySphere.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialDao extends JpaRepository<Material, Integer> {
    // You can add custom query methods here if needed
}

