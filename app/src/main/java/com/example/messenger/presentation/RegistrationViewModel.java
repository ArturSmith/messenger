package com.example.messenger.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.messenger.data.MessengerRepositoryImpl;
import com.example.messenger.domain.GetCurrentUserStateUseCase;
import com.example.messenger.domain.GetUserUseCase;
import com.example.messenger.domain.SignOutUseCase;
import com.example.messenger.domain.SignupWithEmailAndPasswordUseCase;
import com.example.messenger.domain.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationViewModel extends ViewModel {
    private MessengerRepositoryImpl repository;
    private GetUserUseCase getUserUseCase;
    private GetCurrentUserStateUseCase getCurrentUserStateUseCase;
    private SignupWithEmailAndPasswordUseCase signupWithEmailAndPasswordUseCase;
    private MutableLiveData<String> error = new MutableLiveData<>();

    public LiveData<FirebaseUser> getUser() {
        return getUserUseCase.getUser();
    }

    public LiveData<String> getError() {
        return error;
    }

    public RegistrationViewModel() {
        repository = new MessengerRepositoryImpl();
        getUserUseCase = new GetUserUseCase(repository);
        getCurrentUserStateUseCase = new GetCurrentUserStateUseCase(repository);
        signupWithEmailAndPasswordUseCase = new SignupWithEmailAndPasswordUseCase(repository);

        getCurrentUserStateUseCase.getCurrentUser();
    }

    public void signupWithEmailAndPassword(String email, String password, String name, String lastName) {
        signupWithEmailAndPasswordUseCase.signup(email, password, name, lastName, error);
    }


}
