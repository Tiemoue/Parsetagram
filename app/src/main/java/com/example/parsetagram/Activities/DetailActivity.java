package com.example.parsetagram.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.parsetagram.Adaptars.CommentAdaptar;
import com.example.parsetagram.Models.Comment;
import com.example.parsetagram.Models.Post;
import com.example.parsetagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;

import java.util.Date;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private TextView tvUsername;
    private ImageView ivImage;
    private TextView tvDescription;
    private TextView tvCreatedAt;
    private ImageButton ibLikes;
    private ImageButton ibComment;
    private RecyclerView rvComments;
    private TextView tvLikeCounts;
    private ImageView ivDetailProfile;
    CommentAdaptar adapter;
    Post post;


    @Override
    protected void onRestart() {
        super.onRestart();
        reFreshComment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvUsername = findViewById(R.id.tvDetailUsername);

        ivImage = findViewById(R.id.ivDetailImage);

        ivDetailProfile = findViewById(R.id.ivDetailprofile);

        tvDescription = findViewById(R.id.tvDetailDescription);

        tvCreatedAt = findViewById(R.id.tvDate);

        ibLikes = findViewById(R.id.ibLikes);

        ibComment = findViewById(R.id.ibComment);

        rvComments = findViewById(R.id.rvComments);

        tvLikeCounts = findViewById(R.id.tvlikeCounts);

        adapter = new CommentAdaptar();
        rvComments.setLayoutManager(new LinearLayoutManager(this));
        rvComments.setAdapter(adapter);

        post = getIntent().getParcelableExtra(Post.class.getSimpleName());
        Date createdAt = post.getCreatedAt();
        String timeAgo = Post.calculateTimeAgo(createdAt);
        tvUsername.setText(post.getUser().getUsername());
        tvDescription.setText(post.getDescription());
        tvCreatedAt.setText(timeAgo);
        ParseFile profileImage = post.getUser().getParseFile("profileimage");

        if(profileImage != null){
            Glide.with(this).load(profileImage.getUrl()).transform(new RoundedCorners(90)).into(ivDetailProfile);
            ivDetailProfile.setVisibility(View.VISIBLE);
        }else{
            ivDetailProfile.setVisibility(View.GONE);
        }

        ParseFile image = post.getImage();
        if(image != null){
            Glide.with(this).load(image.getUrl()).into(ivImage);
            ivImage.setVisibility(View.VISIBLE);
        }else{
            ivImage.setVisibility(View.GONE);
        }

        if(post.isLikedByCurrentUser()){
            ibLikes.setBackgroundResource(R.drawable.ufi_heart_active);
        }else{
            ibLikes.setBackgroundResource(R.drawable.ufi_heart);
        }

        ibLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(post.isLikedByCurrentUser()){
                  post.unlike();
                    ibLikes.setBackgroundResource(R.drawable.ufi_heart);
                }else{
                   post.like();
                    ibLikes.setBackgroundResource(R.drawable.ufi_heart_active);
                }
                tvLikeCounts.setText(post.getLikesCount());
            }
        });

        tvLikeCounts.setText(post.getLikesCount());
        ibComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, CommentActivity.class);
                intent.putExtra("post", post);
                startActivity(intent);
            }
        });
        reFreshComment();
    }

    public void reFreshComment(){
        ParseQuery<Comment> query = ParseQuery.getQuery("Comment");
        query.whereEqualTo(Comment.KEY_POST, post);
        query.orderByDescending("createdAt");
        query.include(Comment.KEY_AUTHOR);
        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> objects, ParseException e) {
                if(e!= null){
                    return;
                }else{
                    adapter.clear();
                    adapter.addAll(objects);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}