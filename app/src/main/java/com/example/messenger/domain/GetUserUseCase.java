package com.example.messenger.domain;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseUser;

public class GetUserUseCase {
    private MessengerRepository repository;

    public GetUserUseCase(MessengerRepository repository) {
        this.repository = repository;
    }

    public LiveData<FirebaseUser> getUser() {
        return repository.getUserFromFirebase();
    }
}
