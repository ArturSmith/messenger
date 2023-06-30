package com.example.messenger.domain;

public class SignOutUseCase {
    private MessengerRepository repository;

    public SignOutUseCase(MessengerRepository repository) {
        this.repository = repository;
    }

    public void signOut() {
        repository.signOut();
    }
}
