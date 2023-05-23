package com.example.messenger;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

public class RegistrationViewModel extends ViewModel {

    private FirebaseAuth auth;
    private   MutableLiveData<Boolean> isRegistered = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsRegistered() {
        return isRegistered;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public RegistrationViewModel() {
        auth = FirebaseAuth.getInstance();
    }

    public void signupWithEmailAndPassword(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    isRegistered.setValue(true);
                })
                .addOnFailureListener(e -> {
                    error.setValue(e.toString());
                });
    }


}
