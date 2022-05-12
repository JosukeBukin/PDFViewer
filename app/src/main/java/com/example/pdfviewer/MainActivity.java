package com.example.pdfviewer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static final int PICK_PDF_FILE = 2;
    public ArrayList<Doc>recent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ListView lv = (ListView) findViewById(R.id.lv);
        recent = new ArrayList<>();
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
    private void openPDFView(String path) {
        Context c = MainActivity.this;
        Intent i = new Intent(c, DocumentActivity.class);
        i.putExtra("PATH", path);
        c.startActivity(i);
    }
    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if(requestCode == PICK_PDF_FILE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if(resultData != null) {
                uri = resultData.getData();
                Intent intent = new Intent(this, DocumentActivity.class);
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(uri);
                startActivity(intent);
//                Doc doc = new Doc();
//                doc.setUri(uri);
//                File file = new File(uri.getPath());
//                final String[] split = file.getPath().split(":");
//                file = new File(split[1]);
//                doc.setPath(file.getAbsolutePath());
//                doc.setName(file.getName());
//                // recent.add(doc);
//                openPDFView(doc.getPath());
            }
        }
    }
}