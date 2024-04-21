package com.example.StudySphere.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
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

    @OneToMany(mappedBy = "student",cascade = CascadeType.REMOVE)
    private List<Submission> submissions;

    public void addSubmission(Submission submission){
        if(submissions==null){
            submissions=new ArrayList<>();
        }
        submissions.add(submission);
        submission.setStudent(this);
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }

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
