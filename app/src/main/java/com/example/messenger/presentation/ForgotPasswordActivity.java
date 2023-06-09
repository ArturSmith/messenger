package com.example.messenger.presentation;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.messenger.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText email;
    private AppCompatButton resetPasswordButton;
    private ForgotPasswordViewModel viewModel;
    private TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        viewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);
        initViews();
        setSystemBarColor();
        viewModelObserve(ForgotPasswordActivity.this);
        resetPassword();
    }

    private void setSystemBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.GRAY);
    }

    private void resetPassword() {
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = email.getText().toString().trim();
                if (!emailText.isEmpty()) {
                    viewModel.resetPassword(emailText);
                    errorText.setVisibility(View.GONE);
                } else {
                    errorText.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void viewModelObserve(Context context) {
        viewModel.getSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean success) {
                if (success) {
                    Toast.makeText(context, "Email is sent", Toast.LENGTH_SHORT).show();
                    Intent intent = LoginActivity.newIntent(context);
                    startActivity(intent);
                }
            }
        });

        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if (error != null) {
                    Toast.makeText(context, "Wrong email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initViews() {
        email = findViewById(R.id.editTextEmailFPA);
        resetPasswordButton = findViewById(R.id.buttonResetPasswordFPA);
        errorText = findViewById(R.id.errorTextViewFPA);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, ForgotPasswordActivity.class);
    }
}