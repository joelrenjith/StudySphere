package com.example.StudySphere.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subject")
public class Subject {
    @Id
    @Column(name="id")
    private String id;

    @Column(name="anchor_Id")
    private String anchorId;

    @Column(name="name")
    private String name;

    @Column(name="sem")
    private int sem;

    @Column(name="credits")
    private int credits;

    @OneToMany(mappedBy = "subject",cascade = CascadeType.REMOVE)
    private List<Material> materials;

    @OneToMany(mappedBy = "subject",cascade = CascadeType.REMOVE)
    private List<Attendance> attendances;

    @OneToMany(mappedBy = "subject",cascade = CascadeType.REMOVE)
    private List<Assignment> assignments;

    @OneToMany(mappedBy = "subject",cascade = CascadeType.REMOVE)
    private List<Quiz> quizzes;




    public void addMaterial(Material material){
        if(materials==null){
            materials=new ArrayList<>();
        }
        materials.add(material);
        material.setSubject(this);
    }



    public void addAssignment(Assignment assignment){
        if(assignments==null){
            assignments=new ArrayList<>();
        }
        assignments.add(assignment);
        assignment.setSubject(this);
    }

    public void addQuiz(Quiz quiz){
        if(quizzes==null){
            quizzes=new ArrayList<>();
        }
        quizzes.add(quiz);
        quiz.setSubject(this);
    }

    public String  getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnchorId() {
        return anchorId;
    }

    public void setAnchorId(String anchorId) {
        this.anchorId = anchorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSem() {
        return sem;
    }

    public void setSem(int sem) {
        this.sem = sem;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public List<Material> getMaterials() {
        return materials;
    }


    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }
}
