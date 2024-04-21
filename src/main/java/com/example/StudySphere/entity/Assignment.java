package com.example.StudySphere.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="assignment")
@PrimaryKeyJoinColumn(name="id")
public class Assignment extends GradedItem {

    private String file_id;

    @OneToMany(mappedBy = "assignment",cascade = CascadeType.REMOVE)
    private List<Submission> submissions;

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public void addSubmission(Submission submission){
        if(submissions==null){
            submissions=new ArrayList<>();
        }
        submissions.add(submission);
        submission.setAssignment(this);
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }
}
