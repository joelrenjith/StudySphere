package com.example.StudySphere.entity;

import jakarta.persistence.*;

@Entity
@Table(name="material")
public class Material {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="chapter")
    private int chapter;

    @Column(name="file_id")
    private String fileId;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="subject_id")
    private Subject subject;

    public int getId() {
        return id;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Material() {
    }

    public Material(int chapter, String fileId) {
        this.chapter = chapter;
        this.fileId = fileId;

    }

    @Override
    public String toString() {
        return "Material{" +
                "id=" + id +
                ", chapter=" + chapter +
                ", fileId='" + fileId + '\'' +
                ", subject=" + subject +
                '}';
    }
}
