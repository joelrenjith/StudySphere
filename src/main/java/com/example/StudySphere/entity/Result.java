package com.example.StudySphere.entity;

import jakarta.persistence.*;

@Entity
@Table(name="result")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private char grade;

    @OneToOne(cascade = CascadeType.PERSIST
    )
    @JoinColumn(name = "submission_id") // Define the join column
    private Submission submission;


    public char getGrade() {
        return grade;
    }

    public void setGrade(char grade) {
        this.grade = grade;
    }

    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
