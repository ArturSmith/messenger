package com.example.messenger.domain;

import androidx.lifecycle.MutableLiveData;

public class LoginUseCase {

    private MessengerRepository repository;

    public LoginUseCase(MessengerRepository repository) {
        this.repository = repository;
    }

    public void login(String email, String password, MutableLiveData<String> errorOfSignIn){
        repository.login(email, password, errorOfSignIn);
    }
}
