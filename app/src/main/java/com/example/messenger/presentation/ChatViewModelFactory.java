package com.example.messenger.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.messenger.presentation.ChatViewModel;

public class ChatViewModelFactory implements ViewModelProvider.Factory {
    private String currentUserId;
    private String otherUserID;

    public ChatViewModelFactory(String currentUserId, String otherUserID) {
        this.currentUserId = currentUserId;
        this.otherUserID = otherUserID;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ChatViewModel(currentUserId, otherUserID);
    }
}
