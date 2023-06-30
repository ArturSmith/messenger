package com.example.messenger.domain;

import androidx.lifecycle.MutableLiveData;

public class SendMessageUseCase {
    private MessengerRepository repository;

    public SendMessageUseCase(MessengerRepository repository) {
        this.repository = repository;
    }

    public void sendMessage(Message message, MutableLiveData<String> error, MutableLiveData<Boolean> messageSent){
        repository.sendMessage(message, error, messageSent);
    }
}
