package com.example.messenger.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.messenger.data.MessengerRepositoryImpl;
import com.example.messenger.domain.GetCurrentUserStateUseCase;
import com.example.messenger.domain.GetUserUseCase;
import com.example.messenger.domain.LoginUseCase;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends ViewModel {


    private MessengerRepositoryImpl repository;
    // TODO: This is a wrong implementation of casting repository to the viewModel, should be fixed!
    private LoginUseCase loginUseCase;
    private GetUserUseCase getUserUseCase;
    private GetCurrentUserStateUseCase getCurrentUserStateUseCase;
    private MutableLiveData<String> errorOfSignIn = new MutableLiveData<>();


    public LoginViewModel() {
        repository = new MessengerRepositoryImpl();
        loginUseCase = new LoginUseCase(repository);
        getUserUseCase = new GetUserUseCase(repository);
        getCurrentUserStateUseCase = new GetCurrentUserStateUseCase(repository);
        getCurrentUserStateUseCase.getCurrentUser();
    }

    public void login(String email, String password) {
      loginUseCase.login(email, password, errorOfSignIn);
    }

    public LiveData<FirebaseUser> getUser() {
        return getUserUseCase.getUser();
    }

    public LiveData<String> getErrorOfSignIn() {
        return errorOfSignIn;
    }


}
