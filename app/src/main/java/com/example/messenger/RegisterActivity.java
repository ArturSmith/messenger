package com.example.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    private EditText name;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private EditText confirmedPassword;
    private TextView errorText1;
    private TextView buttonRegister;
    private RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        initViews();

        signup(RegisterActivity.this);

        viewModel.isRegistered.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isRegistered) {
                if (isRegistered) {
                    Intent intent = MainActivity.newIntent(RegisterActivity.this);
                    startActivity(intent);
                }
            }
        });
    }

    private void signup(Context context) {
        buttonRegister.setOnClickListener(view -> {
            String nameText = name.getText().toString().trim();
            String lastNameText = lastName.getText().toString().trim();
            String emailText = email.getText().toString().trim();
            String passwordText = password.getText().toString().trim();
            String confirmedPasswordText = confirmedPassword.getText().toString().trim();
            if (nameText.isEmpty() ||
                    lastNameText.isEmpty() ||
                    emailText.isEmpty() ||
                    passwordText.isEmpty() ||
                    confirmedPasswordText.isEmpty()) {
                errorText1.setText("Enter all fields");
            } else {
                if (!passwordText.equals(confirmedPasswordText)) {
                    errorText1.setText("Confirm password");
                } else {
                    errorText1.setText("");
                    viewModel.signupWithEmailAndPassword(emailText, passwordText, context);
                }
            }
        });
    }

    private void initViews() {
        name = findViewById(R.id.editTextNameRA);
        lastName = findViewById(R.id.editTextLastNameRA);
        email = findViewById(R.id.editTextEmailRA);
        password = findViewById(R.id.editTextPasswordRA);
        confirmedPassword = findViewById(R.id.editTextConfirmPasswordRA);
        errorText1 = findViewById(R.id.errorTextRA);
        buttonRegister = findViewById(R.id.buttonRegisterRA);

    }

    public static Intent newIntent(Context context) {
        return new Intent(context, RegisterActivity.class);
    }
}