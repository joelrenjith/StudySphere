package com.example.StudySphere.entity;

import jakarta.persistence.*;

@Entity
@Table(name ="teachers")
@PrimaryKeyJoinColumn(name="uid")
public class Teacher extends User{



    @Enumerated(EnumType.STRING)
    @Column(name = "department")
    private Departments dept;


    public Departments getDept() {
        return dept;
    }

    public void setDept(Departments dept) {
        this.dept = dept;
    }
}
