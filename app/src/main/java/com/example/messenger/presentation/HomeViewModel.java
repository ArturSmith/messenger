package com.example.messenger.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.messenger.data.MessengerRepositoryImpl;
import com.example.messenger.domain.GetCurrentUserStateUseCase;
import com.example.messenger.domain.GetUserUseCase;
import com.example.messenger.domain.GetUsersUseCase;
import com.example.messenger.domain.LoadUsersUseCase;
import com.example.messenger.domain.SetCurrentUserOnlineUseCase;
import com.example.messenger.domain.SignOutUseCase;
import com.example.messenger.domain.User;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private MessengerRepositoryImpl repository;
    private GetCurrentUserStateUseCase getCurrentUserStateUseCase;
    private GetUserUseCase getUserUseCase;
    private GetUsersUseCase getUsersUseCase;
    private LoadUsersUseCase loadUsersUseCase;
    private SetCurrentUserOnlineUseCase setCurrentUserOnlineUseCase;
    private SignOutUseCase signOutUseCase;

    public HomeViewModel() {
        repository = new MessengerRepositoryImpl();
        getCurrentUserStateUseCase = new GetCurrentUserStateUseCase(repository);
        getUserUseCase = new GetUserUseCase(repository);
        getUsersUseCase = new GetUsersUseCase(repository);
        loadUsersUseCase = new LoadUsersUseCase(repository);
        setCurrentUserOnlineUseCase = new SetCurrentUserOnlineUseCase(repository);
        signOutUseCase = new SignOutUseCase(repository);
        getCurrentUserStateUseCase.getCurrentUser();
        loadUsers();
    }

    public LiveData<FirebaseUser> getUser() {
        return getUserUseCase.getUser();
    }
    public void setUserOnline(Boolean isOnline) {
        setCurrentUserOnlineUseCase.setCurrentUserOnline(isOnline);
    }

    public void loadUsers() {
       loadUsersUseCase.loadUsers();
    }

    public void logout() {
        signOutUseCase.signOut();
    }

    public LiveData<List<User>> getUsers() {
        return getUsersUseCase.getUsers();
    }
}
