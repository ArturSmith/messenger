package com.example.messenger.presentation;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.messenger.data.MessengerRepositoryImpl;
import com.example.messenger.domain.ResetPasswordUseCase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordViewModel extends ViewModel {

    private MessengerRepositoryImpl repository;
    private ResetPasswordUseCase resetPasswordUseCase;
    private MutableLiveData<Boolean> success = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    public LiveData<Boolean> getSuccess() {
        return success;
    }

    public LiveData<String> getError() {
        return error;
    }

    public ForgotPasswordViewModel() {
        repository = new MessengerRepositoryImpl();
        resetPasswordUseCase = new ResetPasswordUseCase(repository);
    }

    public void resetPassword(String email) {
        resetPasswordUseCase.resetPassword(email, error, success);
    }
}
