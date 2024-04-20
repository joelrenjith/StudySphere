package com.example.StudySphere.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name ="assignment")
@PrimaryKeyJoinColumn(name="id")
public class Assignment extends GradedItem {

    private String file_id;

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }
}
