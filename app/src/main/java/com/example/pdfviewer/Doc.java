package com.example.pdfviewer;

import android.net.Uri;

import java.io.File;

public class Doc {
    String name, path;
    Uri uri;
    public String getName() {
        return name;
    }
    public String getPath() {
        return path;
    }
    public Uri getUri() {return  uri;}
    public void setName(String name) {
        this.name = name;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public void setUri(Uri uri) {this.uri = uri;}
}


