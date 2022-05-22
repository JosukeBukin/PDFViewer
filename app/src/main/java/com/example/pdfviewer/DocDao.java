package com.example.pdfviewer;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

@Dao
public interface DocDao {
    @Query("SELECT * FROM doc")
    List<Doc> getAll();
    @Query("SELECT * FROM doc WHERE file_uri LIKE :uri LIMIT 1")
    Doc findDocByPath(String uri);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Doc... docs);
    @Delete
    void delete(Doc doc);
}

