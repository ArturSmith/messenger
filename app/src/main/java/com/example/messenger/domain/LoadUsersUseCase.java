package com.example.messenger.domain;

public class LoadUsersUseCase {
    private MessengerRepository repository;

    public LoadUsersUseCase(MessengerRepository repository) {
        this.repository = repository;
    }

    public void loadUsers() {
        repository.loadUsers();
    }
}
