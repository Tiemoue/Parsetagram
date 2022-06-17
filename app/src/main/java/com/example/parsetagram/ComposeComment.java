package com.example.parsetagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ComposeComment extends AppCompatActivity {

    Post post;
    EditText etBody;
    Button btSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_comment);
        etBody = findViewById(R.id.etBody);
        btSave = findViewById(R.id.btSave);
        post = getIntent().getParcelableExtra("post");

        Toast.makeText(this, post.getDescription(), Toast.LENGTH_SHORT).show();
        Comment comment = new Comment();
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // post the new
                Comment comment = new Comment();
                comment.setAuthor(ParseUser.getCurrentUser());
                comment.setBody(etBody.getText().toString());
                comment.setPost(post);
                comment.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e != null){
                            return;
                        }else{
                            finish();
                        }
                    }
                });
            }
        });
    }
}