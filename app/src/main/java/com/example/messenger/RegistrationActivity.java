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
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    private EditText name;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private EditText confirmedPassword;
    private TextView errorText1;
    private AppCompatButton buttonRegister;
    private RegistrationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        viewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
        initViews();
        signup();
        viewModelObserve();
        setSystemBarColor();
    }

    private void viewModelObserve() {
        viewModel.getIsRegistered().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isRegistered) {
                if (isRegistered) {
                    Toast.makeText(
                            RegistrationActivity.this,
                            "User created",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = MainActivity.newIntent(RegistrationActivity.this);
                    startActivity(intent);
                }
            }
        });

        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if (error != null) {
                    Toast.makeText(
                            RegistrationActivity.this,
                            error,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setSystemBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.GRAY);
    }

    private void signup() {
        buttonRegister.setOnClickListener(view -> {
            String nameText = getTrimmedString(name);
            String lastNameText = getTrimmedString(lastName);
            String emailText = getTrimmedString(email);
            String passwordText = getTrimmedString(password);
            String confirmedPasswordText = getTrimmedString(confirmedPassword);
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
                    viewModel.signupWithEmailAndPassword(emailText, passwordText);
                }
            }
        });
    }

    private String getTrimmedString(EditText text) {
        return text.getText().toString().trim();
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