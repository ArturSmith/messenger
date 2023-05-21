package com.example.messenger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {


    private EditText name;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private EditText confirmedPassword;
    private TextView errorText;
    private TextView buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
    }

    private void initViews(){
        name = findViewById(R.id.editTextNameRA);
        lastName = findViewById(R.id.editTextLastNameRA);
        email = findViewById(R.id.editTextEmailRA);
        password = findViewById(R.id.editTextPasswordRA);
        confirmedPassword = findViewById(R.id.editTextConfirmPasswordRA);
        errorText = findViewById(R.id.errorTextRA);
        buttonRegister = findViewById(R.id.buttonRegisterRA);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, RegisterActivity.class);
    }
}