package com.example.messenger.domain;

import androidx.lifecycle.LiveData;

public class GetOtherUserUseCase {
    private MessengerRepository repository;

    public GetOtherUserUseCase(MessengerRepository repository) {
        this.repository = repository;
    }

    public LiveData<User> getOtherUser(String otherUserId){
        return repository.getOtherUser(otherUserId);
    }
}
