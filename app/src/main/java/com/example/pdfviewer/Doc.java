package com.example.pdfviewer;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Doc {
    @PrimaryKey(autoGenerate = true)
            public int uid;
    @ColumnInfo(name = "file_name")
            public String name;
    @ColumnInfo(name = "file_path")
            public String path;
    @ColumnInfo(name = "file_uri")
            public String uri;
}


