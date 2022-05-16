package com.example.pdfviewer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static final int PICK_PDF_FILE = 2;
    private static final String SHARED_PREFERENCES_FILE_DOCUMENT_INFO = "documentInfoList";
    private static final String SHARED_PREFERENCES_DOCUMENT_INFO = "Document_Info_List";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // ArrayList<Doc> recent = loadData();
        // EMPTY LIST
        ArrayList<Doc> recent = new ArrayList<Doc>();
        final ListView lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(new CustomAdapter(MainActivity.this, recent));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFile();
            }
        });
    }

    private void openFile() {
        Intent safIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        safIntent.addCategory(Intent.CATEGORY_OPENABLE);
        safIntent.setType("application/pdf");
        startActivityForResult(safIntent, 2);

    }
    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if(requestCode == PICK_PDF_FILE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if(resultData != null) {
                uri = resultData.getData();
                Intent intent = new Intent(this, DocumentActivity.class);
                intent.setData(uri);
                // SAVE INFORMATION ABOUT FILE
                Doc doc = new Doc();
                doc.setUri(uri);
                File file = new File(uri.getPath());
                final String[] split = file.getPath().split(":");
                file = new File(split[1]);
                doc.setPath(file.getAbsolutePath());
                doc.setName(file.getName());
                // SAVE INFORMATION
                saveData(doc);
                // START ACTIVITY
                startActivity(intent);
            }
        }
    }
    private void saveData(Doc doc) {
        Gson gson = new Gson();
        String jsonInfo = gson.toJson(doc);

        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE_DOCUMENT_INFO, MODE_PRIVATE);
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();

        editor.putString(SHARED_PREFERENCES_DOCUMENT_INFO, jsonInfo);
        editor.commit();
    }
    private ArrayList<Doc> loadData() {
        Context context = getApplicationContext();

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE_DOCUMENT_INFO, MODE_PRIVATE);
        String docInfoList = sharedPreferences.getString(SHARED_PREFERENCES_DOCUMENT_INFO, "");

        Gson gson = new Gson();
        ArrayList<Doc> Docs = gson.fromJson(docInfoList, ArrayList.class);
        return Docs;
    }
}