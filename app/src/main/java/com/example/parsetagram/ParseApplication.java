package com.example.parsetagram;

import android.app.Application;

import com.example.parsetagram.Models.Comment;
import com.example.parsetagram.Models.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(Comment.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("WBKRlAViXZ9fyoYCAwFQkzMbM0ykRVgwsj6Izovg")
                .clientKey("XfkTYzYvXIwXhH5tmaCtdxU4aq4FpNOleXPofi0V")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
