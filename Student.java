package com.example.project_aftab_hasan;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Student {

    @PrimaryKey
    int id;
    int score;
    String comment;

    public Student(int id, int score, String comment) {
        this.id = id;
        this.score = score;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return id + "\t\t\t\t\t\t\t\t\t\t\t" + score + "\t\t\t\t\t\t\t\t\t\t" + comment;
    }

}
