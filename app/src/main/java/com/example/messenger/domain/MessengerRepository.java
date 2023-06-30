package com.example.messenger.domain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public interface MessengerRepository {
    void login(String email, String password, MutableLiveData<String> errorOfSignIn);
    LiveData<FirebaseUser> getUserFromFirebase();
    void getCurrentUser();
    void loadUsers();
    void signOut();
    LiveData<List<User>> getUsers();
    void resetPassword(String email, MutableLiveData<String> error, MutableLiveData<Boolean> success);
    void signup(String email, String password, String name, String lastName, MutableLiveData<String> error);
    void setUserOnline(String userId, Boolean isOnline);
    void setCurrentUserOnline(Boolean isOnline);
    LiveData<User> getOtherUser(String otherUserId);
    LiveData<List<Message>> getMessages(String currentUserId, String otherUserId);
    void sendMessage(Message message, MutableLiveData<String> error, MutableLiveData<Boolean> messageSent);
}
