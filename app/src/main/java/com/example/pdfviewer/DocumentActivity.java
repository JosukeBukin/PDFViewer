package com.example.pdfviewer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;

public class DocumentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        PDFView pdfView = (PDFView) findViewById(R.id.documentView);

        Intent i = this.getIntent();
        Uri uri = i.getData();
        pdfView.fromUri(uri).defaultPage(1).onLoad(new OnLoadCompleteListener() {
            @Override
            public void loadComplete(int nbPages) {
                Toast.makeText(DocumentActivity.this, String.valueOf(nbPages), Toast.LENGTH_LONG).show();
            }
        }).load();;
    }
}