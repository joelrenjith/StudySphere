package com.example.StudySphere.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="teachers")
@PrimaryKeyJoinColumn(name="uid")
public class Teacher extends User{



    @Enumerated(EnumType.STRING)
    @Column(name = "department")
    private Departments dept;

    @OneToMany(mappedBy = "teacher",cascade = CascadeType.REMOVE)
    private List<Assignment> assignments;

    @OneToMany(mappedBy = "teacher",cascade = CascadeType.REMOVE)
    private List<Quiz> quizzes;

    public void addAssignment(Assignment assignment){
        if(assignments==null){
            assignments=new ArrayList<>();
        }
        assignments.add(assignment);
        assignment.setTeacher(this);
    }

    public void addQuiz(Quiz quiz){
        if(quizzes==null){
            quizzes=new ArrayList<>();
        }
        quizzes.add(quiz);
        quiz.setTeacher(this);
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

    public Departments getDept() {
        return dept;
    }

    public void setDept(Departments dept) {
        this.dept = dept;
    }
}
