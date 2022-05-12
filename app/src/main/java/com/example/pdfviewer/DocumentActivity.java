package com.example.pdfviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;

import java.io.File;

public class DocumentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        PDFView pdfView = (PDFView) findViewById(R.id.documentView);

        Intent i = this.getIntent();
        String path = i.getExtras().getString("PATH");
        File file = new File(path);
        if (file.canRead()) {
            pdfView.fromFile(file).defaultPage(1).onLoad(new OnLoadCompleteListener() {
                @Override
                public void loadComplete(int nbPages) {
                    Toast.makeText(DocumentActivity.this, String.valueOf(nbPages), Toast.LENGTH_SHORT).show();
                }
            }).load();
        }
    }
}