package com.example.parsetagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    private TextView tvUsername;
    private ImageView ivImage;
    private TextView tvDescription;
    private TextView tvCreatedAt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvUsername = findViewById(R.id.tvDetailUsername);
        ivImage = findViewById(R.id.ivDetailImage);
        tvDescription = findViewById(R.id.tvDetailDescription);
        tvCreatedAt = findViewById(R.id.tvDetailCreatedAt);



        Post post = getIntent().getParcelableExtra(Post.class.getSimpleName());
        Date createdAt = post.getCreatedAt();
        String timeAgo = Post.calculateTimeAgo(createdAt);
        tvUsername.setText(post.getUser().getUsername());
        tvDescription.setText(post.getDescription());
        tvCreatedAt.setText(timeAgo);

        ParseFile image = post.getImage();
        if(image != null){
            Glide.with(this).load(image.getUrl()).into(ivImage);
            ivImage.setVisibility(View.VISIBLE);
        }else{
            ivImage.setVisibility(View.GONE);
        }

    }
}