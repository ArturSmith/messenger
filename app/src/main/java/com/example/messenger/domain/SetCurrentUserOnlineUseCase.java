package com.example.messenger.domain;

public class SetCurrentUserOnlineUseCase {
    private MessengerRepository repository;

    public SetCurrentUserOnlineUseCase(MessengerRepository repository) {
        this.repository = repository;
    }

    public void setCurrentUserOnline(Boolean isOnline) {
        repository.setCurrentUserOnline(isOnline);
    }
}
