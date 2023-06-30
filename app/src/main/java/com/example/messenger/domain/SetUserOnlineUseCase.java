package com.example.messenger.domain;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SetUserOnlineUseCase {
    private MessengerRepository repository;

    public SetUserOnlineUseCase(MessengerRepository repository) {
        this.repository = repository;
    }

    public void setUserOnline(String userId, Boolean isOnline) {
        repository.setUserOnline(userId, isOnline);
    }
}
