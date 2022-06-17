package com.example.parsetagram;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.RoundedCorner;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.parse.ParseFile;

import java.util.Date;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvUsername;
        private ImageView ivImage;
        private TextView tvDescription;
        private TextView tvCreatedAt;
        private TextView tvLikes;
        private ImageButton feedLikes;
        private  ImageView profile;
        public static  final String KEY_PROFILE_IMAGE = "profileimage";

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
            tvLikes = itemView.findViewById(R.id.tvLikes);
            feedLikes = itemView.findViewById(R.id.feedLike);
            profile = itemView.findViewById(R.id.profile);
            itemView.setOnClickListener(this);
        }

        public void bind(Post post) {
            Date createdAt = post.getCreatedAt();
            String timeAgo = Post.calculateTimeAgo(createdAt);
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            tvCreatedAt.setText(timeAgo);
            tvLikes.setText(post.getLikesCount());
            ParseFile profilePicture = post.getUser().getParseFile(KEY_PROFILE_IMAGE);

            if (post.isLikedByCurrentUser()){
                feedLikes.setBackgroundResource(R.drawable.ufi_heart_active);
            }else{
                feedLikes.setBackgroundResource(R.drawable.ufi_heart);
            }

            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
                ivImage.setVisibility(View.VISIBLE);
            }else{
                ivImage.setVisibility(View.GONE);
            }

            if(profilePicture != null){
                Glide.with(context).load(profilePicture.getUrl()).transform(new RoundedCorners(90)).into(profile);
            }else{
                ivImage.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION){
                Post post = posts.get(position);
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(Post.class.getSimpleName(), posts.get(position));
                context.startActivity(intent);
            }
        }
    }
}
