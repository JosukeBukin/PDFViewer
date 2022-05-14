package com.example.pdfviewer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context c;
    ArrayList<Doc> Docs;

    public CustomAdapter(Context c, ArrayList<Doc> Docs) {
        this.c = c;
        this.Docs = Docs;
    }

    @Override
    public int getCount() {
        return Docs.size();
    }

    @Override
    public Object getItem(int i) {
        return Docs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
        {
            //INFLATE CUSTOM LAYOUT
            view= LayoutInflater.from(c).inflate(R.layout.model,viewGroup,false);
        }

        final Doc Doc= (Doc) this.getItem(i);

        TextView nameTxt= (TextView) view.findViewById(R.id.nameTxt);
        ImageView img= (ImageView) view.findViewById(R.id.pdfImage);

        //BIND DATA
        nameTxt.setText(Doc.getName());
        img.setImageResource(R.drawable.pdf_icon);

        //VIEW ITEM CLICK
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPDFView(Doc.getUri());
            }
        });
        return view;
    }

    //OPEN PDF VIEW
    private void openPDFView(Uri uri) {
        Intent intent = new Intent(c,DocumentActivity.class);
        intent.setData(uri);
        c.startActivity(intent);
    }
}
