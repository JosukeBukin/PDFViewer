package com.example.pdfviewer;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.documentfile.provider.DocumentFile;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final int PICK_PDF_FILE = 2;
    public static final int NUMBER_OF_THREADS = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        List<Doc> recent = null;
        recent = getInfo(MainActivity.this);
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
                final int takeFlags = intent.getFlags()
                        & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                        | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                getContentResolver().takePersistableUriPermission(uri, takeFlags);

                Doc doc = new Doc();
                doc.uri = uri.toString();
                File file = new File(uri.getPath());
                final String[] split = file.getPath().split(":");
                file = new File(split[1]);
                doc.path = file.getAbsolutePath();
                doc.name = file.getName();

                doc.uri = makeLocalCopy(doc).toString();
                saveInfo(doc, MainActivity.this);

                // START ACTIVITY
                startActivity(intent);
            }
        }
    }
    // MAKE A LOCAL COPY OF DOCUMENT
    private Uri makeLocalCopy(Doc doc) {
        Uri result = null;
        ContentResolver contentResolver = getContentResolver();
        DocumentFile parent = DocumentFile.fromSingleUri(MainActivity.this, Uri.parse(doc.uri));
        if(doc.name != null) {
            try {
                File f = File.createTempFile("cw_", ".pdf", getCacheDir());
                InputStream in = contentResolver.openInputStream(parent.getUri());
                FileOutputStream out = new FileOutputStream(f);
                try {
                    IOUtils.copy(in, out);
                } finally {
                    out.close();
                    in.close();
                }
                result = Uri.fromFile(f);
            } catch (Exception e) {
                Log.e(getClass().getSimpleName(), "Exception copying to file", e);
            }
        }
        return result;
    }
    // SAVE INFORMATION ABOUT FILE
    public void saveInfo(Doc doc, Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        DocDao docDao = db.docDao();
        if (docDao.findDocByPath(doc.uri) == null) {
            docDao.insertAll(doc);
        }
    }
    public List<Doc> getInfo(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        DocDao docDao = db.docDao();
        List<Doc> recent = docDao.getAll();
        return recent;
    }
}