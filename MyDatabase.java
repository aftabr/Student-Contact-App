package com.example.project_aftab_hasan;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Student.class, version = 1)
public abstract class MyDatabase extends RoomDatabase {

    public abstract StudentDao studentDao();

    private static MyDatabase instance;

    public static MyDatabase getInstance(Context context){

         if(instance == null){

             instance = Room.databaseBuilder(context,MyDatabase.class,"StudentDb")
                     .allowMainThreadQueries()
                     .build();
         }

        return instance;

    }
}
