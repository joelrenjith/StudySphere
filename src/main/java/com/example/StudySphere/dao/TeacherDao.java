package com.example.StudySphere.dao;

import com.example.StudySphere.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherDao extends JpaRepository<Teacher, String> {


}
