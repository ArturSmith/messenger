package com.example.messenger;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterViewModel extends AndroidViewModel {

    private FirebaseAuth auth;
    public  MutableLiveData<Boolean> isRegistered = new MutableLiveData<>();

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();

    }

    public void signupWithEmailAndPassword(String email, String password, Context context) {

        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    Toast.makeText(context, "User created", Toast.LENGTH_SHORT).show();
                    isRegistered.setValue(true);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                    isRegistered.setValue(false);
                });
    }


}
