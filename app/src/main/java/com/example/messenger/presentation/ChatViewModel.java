package com.example.messenger.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.messenger.data.MessengerRepositoryImpl;
import com.example.messenger.domain.GetMessagesUseCase;
import com.example.messenger.domain.GetOtherUserUseCase;
import com.example.messenger.domain.Message;
import com.example.messenger.domain.SendMessageUseCase;
import com.example.messenger.domain.SetUserOnlineUseCase;
import com.example.messenger.domain.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatViewModel extends ViewModel {

    private MessengerRepositoryImpl repository;
    private GetOtherUserUseCase getOtherUserUseCase;
    private SetUserOnlineUseCase setUserOnlineUseCase;
    private GetMessagesUseCase getMessagesUseCase;
    private SendMessageUseCase sendMessageUseCase;


    private MutableLiveData<Boolean> messageSent = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();


    private String currentUserId;
    private String otherUserId;

    public ChatViewModel(String currentUserId, String otherUserId) {
        this.currentUserId = currentUserId;
        this.otherUserId = otherUserId;
        repository = new MessengerRepositoryImpl();
        getOtherUserUseCase = new GetOtherUserUseCase(repository);
        setUserOnlineUseCase = new SetUserOnlineUseCase(repository);
        getMessagesUseCase = new GetMessagesUseCase(repository);
        sendMessageUseCase = new SendMessageUseCase(repository);
        listeners();
    }

    private void listeners() {


    }

    public void setUserOnline(Boolean isOnline) {
        setUserOnlineUseCase.setUserOnline(currentUserId, isOnline);
    }

    public LiveData<List<Message>> getMessages() {
        return getMessagesUseCase.getMessages(currentUserId, otherUserId);
    }

    public LiveData<User> getOtherUser() {
        return getOtherUserUseCase.getOtherUser(otherUserId);
    }

    public LiveData<Boolean> getMessageSent() {
        return messageSent;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void sendMessage(Message message) {
        sendMessageUseCase.sendMessage(message, error, messageSent);
    }
}
