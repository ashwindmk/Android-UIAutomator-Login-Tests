package com.example.ashwin.uiautomatorlogintests;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText mUsernameEditText, mPasswordEditText;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsernameEditText = (EditText) findViewById(R.id.usernameEditText);
        mPasswordEditText = (EditText) findViewById(R.id.passwordEditText);
        mLoginButton = (Button) findViewById(R.id.loginButton);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateLogin()) {
                    goToHome();
                    Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean validateLogin() {
        if ( ("admin").equals(mUsernameEditText.getText().toString()) && ("password").equals(mPasswordEditText.getText().toString()) ) {
            return true;
        } else {
            return false;
        }
    }

    private void goToHome() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra("username", mUsernameEditText.getText().toString());
        intent.putExtra("password", mPasswordEditText.getText().toString());
        startActivity(intent);
    }
}
