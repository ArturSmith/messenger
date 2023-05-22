package com.example.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class RegistrationActivity extends AppCompatActivity {

    private EditText name;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private EditText confirmedPassword;
    private TextView errorText1;
    private AppCompatButton buttonRegister;
    private RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        initViews();

        signup(RegistrationActivity.this);
        setSystemBarColor();

        viewModel.isRegistered.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isRegistered) {
                if (isRegistered) {
                    Intent intent = MainActivity.newIntent(RegistrationActivity.this);
                    startActivity(intent);
                }
            }
        });
    }
    private void setSystemBarColor(){
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.GRAY);
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
        return new Intent(context, RegistrationActivity.class);
    }
}