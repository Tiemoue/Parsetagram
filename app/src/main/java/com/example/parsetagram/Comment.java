package com.example.parsetagram;


import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Comment")
public class Comment extends ParseObject {

    public static final String KEY_AUTHOR= "author";
    public static  final String KEY_POST = "post";
    public static  final String KEY_BODY = "body";


    public ParseUser getAuthor(){
        return  (ParseUser) getParseUser(KEY_AUTHOR);
    }

    public  void setAuthor(ParseUser user) {
        put(KEY_AUTHOR, user);
    }
    public Post getPost(){
        return  (Post) getParseObject(KEY_POST);

    }

    public  void setPost(Post post) {
        put(KEY_POST, post);
    }
    public String getBody(){
        return  getString(KEY_BODY);
    }

    public  void setBody(String body) {
        put(KEY_BODY, body);
    }
}
