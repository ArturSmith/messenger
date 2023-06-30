package com.example.messenger.domain;

import androidx.lifecycle.LiveData;

import java.util.List;

public class GetMessagesUseCase {
    private MessengerRepository repository;

    public GetMessagesUseCase(MessengerRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Message>> getMessages(String currentUserId, String otherUserId){
        return repository.getMessages(currentUserId, otherUserId);
    }
}
