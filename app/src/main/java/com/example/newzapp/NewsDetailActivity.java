package com.example.newzapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {
    private String title, description, content,imageurl,url;

    private TextView newsheading2,subheading2,TVcontent;
    private Button button;
    private ImageView IVNews2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        content = getIntent().getStringExtra("content");
        imageurl = getIntent().getStringExtra("image");
        url = getIntent().getStringExtra("url");

        newsheading2 = findViewById(R.id.newsheading2);
        subheading2 = findViewById(R.id.subheading2);
        TVcontent = findViewById(R.id.TVcontent);
        button = findViewById(R.id.button);
        IVNews2 = findViewById(R.id.IVNews2);

        newsheading2.setText(title);
        subheading2.setText(description);
        TVcontent.setText(content);

        Picasso.get().load(imageurl).into(IVNews2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }
}