package com.example.parsetagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSignupActivity();
            }
        });
    }

    private void loginUser(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null){
                    Log.e("issue with log", String.valueOf(e));
                    Toast.makeText(LoginActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                }else{
                    goToFeedActivity();
                    Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goToComposeActivity() {
        Intent intent = new Intent(this, ComposeActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToFeedActivity() {
        Intent intent = new Intent(LoginActivity.this, FeedActivity.class);
        startActivity(intent);
    }

    private void goToSignupActivity() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
}