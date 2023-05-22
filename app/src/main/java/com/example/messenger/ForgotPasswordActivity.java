package com.example.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText email;
    private AppCompatButton resetPasswordButton;
    private ForgotPasswordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        setSystemBarColor();
        viewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);
        initViews();
        startMainActivity(ForgotPasswordActivity.this);
        resetPassword();
    }
    private void setSystemBarColor(){
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.GRAY);
    }

    private void resetPassword() {
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = email.getText().toString().trim();
                if (!emailText.isEmpty()) {
                    viewModel.resetPassword(emailText, ForgotPasswordActivity.this);
                }
            }
        });
    }

    private void startMainActivity(Context context) {
        viewModel.success.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean success) {
                if (success) {
                    Intent intent = MainActivity.newIntent(context);
                    startActivity(intent);
                }
            }
        });
    }

    private void initViews() {
        email = findViewById(R.id.editTextEmailFPA);
        resetPasswordButton = findViewById(R.id.buttonResetPasswordFPA);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, ForgotPasswordActivity.class);
    }
}