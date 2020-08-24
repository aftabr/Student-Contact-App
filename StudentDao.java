package com.example.project_aftab_hasan;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class StudentDao {

    @Insert
    public abstract void insert(Student student);

    @Delete
    public abstract void delete(Student student);

    @Update
    public abstract void update(Student student);

    @Query("select * from Student")
     public abstract  List<Student> getAll();

    @Query("Select * from Student where id=:id")
    public abstract Student getStudent(int id);
}
