package com.example.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private TextView loginButton;
    private TextView forgotPasswordButton;
    private TextView registerButton;
    private TextView errorText;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        initViews();
        login();
        startRegisterActivity(MainActivity.this);
        startForgorPasswordActivity(MainActivity.this);

        viewModel.authorized.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean authorized) {
                if (authorized) {
                    Intent intent = HomeActivity.newIntent(MainActivity.this);
                    startActivity(intent);
                }
            }
        });
    }

    private void login() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = email.getText().toString().trim();
                String passwordText = password.getText().toString().trim();
                if (emailText.isEmpty() || passwordText.isEmpty()) {
                    errorText.setVisibility(View.VISIBLE);
                } else {
                    errorText.setVisibility(View.GONE);
                    viewModel.login(emailText, passwordText, MainActivity.this);
                }
            }
        });
    }

    private void startRegisterActivity(Context context) {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = RegisterActivity.newIntent(context);
                startActivity(intent);
            }
        });
    }

    private void startForgorPasswordActivity(Context context) {
        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ForgotPasswordActivity.newIntent(context);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        email = findViewById(R.id.editTextEmailAddress);
        password = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);
        forgotPasswordButton = findViewById(R.id.textViewForgotPassword);
        registerButton = findViewById(R.id.textViewRegister);
        errorText = findViewById(R.id.errorText);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
}