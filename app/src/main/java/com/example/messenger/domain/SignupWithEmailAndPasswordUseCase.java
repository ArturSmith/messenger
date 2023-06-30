package com.example.messenger.domain;

import androidx.lifecycle.MutableLiveData;

public class SignupWithEmailAndPasswordUseCase {
    private MessengerRepository repository;

    public SignupWithEmailAndPasswordUseCase(MessengerRepository repository) {
        this.repository = repository;
    }

    public void signup(String email, String password, String name, String lastName, MutableLiveData<String> error){
        repository.signup(email, password, name, lastName, error);
    }
}
