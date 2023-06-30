package com.example.messenger.domain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ResetPasswordUseCase {

    private MessengerRepository repository;

    public ResetPasswordUseCase(MessengerRepository repository) {
        this.repository = repository;
    }

    public void resetPassword(String email, MutableLiveData<String> error, MutableLiveData<Boolean> success) {
        repository.resetPassword(email, error, success);
    }
}
