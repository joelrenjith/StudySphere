package com.example.StudySphere.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="quiz")
@PrimaryKeyJoinColumn(name="id")
public class Quiz extends GradedItem {

    @OneToMany(mappedBy = "quiz",cascade = CascadeType.REMOVE)
    private List<Question> questions;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void addQuestions(Question question) {
        if(questions==null){
            questions=new ArrayList<>();
        }
        questions.add(question);
        question.setQuiz(this);
    }
}
