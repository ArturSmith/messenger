package com.example.messenger.domain;

import androidx.lifecycle.LiveData;

import java.util.List;

public class GetUsersUseCase {
    private MessengerRepository repository;

    public GetUsersUseCase(MessengerRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<User>> getUsers() {
        return repository.getUsers();
    }
}
