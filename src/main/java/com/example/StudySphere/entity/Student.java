package com.example.StudySphere.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name ="students")
@PrimaryKeyJoinColumn(name="uid")
public class Student extends User{

    @Column(name = "section")
    private char section;
    @Enumerated(EnumType.STRING)
    @Column(name = "department")
    private Departments dept;

    @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
    private List<Attendance> attendances;

    public char getSection() {
        return section;
    }

    public void setSection(char section) {
        this.section = section;
    }

    public Departments getDept() {
        return dept;
    }

    public void setDept(Departments dept) {
        this.dept = dept;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }
}
