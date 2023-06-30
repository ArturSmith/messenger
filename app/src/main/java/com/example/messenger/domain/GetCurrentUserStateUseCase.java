package com.example.messenger.domain;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseUser;

public class GetCurrentUserStateUseCase {
    private MessengerRepository repository;

    public GetCurrentUserStateUseCase(MessengerRepository repository) {
        this.repository = repository;
    }

    public void getCurrentUser() {
         repository.getCurrentUser();
    }
}
