package com.example.pdfviewer;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = {Doc.class},
        version = 1
        )
public abstract class AppDatabase extends RoomDatabase {
    public abstract DocDao docDao();
    private static AppDatabase instance;
    static AppDatabase getDatabase(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "recent-files.db").allowMainThreadQueries().build();
        }
        return instance;
    }
}
