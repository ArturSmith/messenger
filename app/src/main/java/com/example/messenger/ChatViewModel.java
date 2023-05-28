package com.example.messenger;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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

    private MutableLiveData<List<Message>> messages = new MutableLiveData<>();
    private MutableLiveData<User> otherUser = new MutableLiveData<>();
    private MutableLiveData<Boolean> messageSent = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference ref = firebaseDatabase.getReference();
    private DatabaseReference refUsers = ref.child("Users");
    private DatabaseReference refMessages = ref.child("Messages");
    private String currentUserId;
    private String otherUserId;

    public ChatViewModel(String currentUserId, String otherUserId) {
        this.currentUserId = currentUserId;
        this.otherUserId = otherUserId;
        listeners();
    }

    private void listeners() {

        refUsers.child(otherUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                otherUser.setValue(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        refMessages.child(currentUserId).child(otherUserId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<Message> messagesList = new ArrayList<>();
                        for (DataSnapshot datasnapshot :
                                snapshot.getChildren()) {
                            Message message = datasnapshot.getValue(Message.class);
                            messagesList.add(message);
                        }
                        messages.setValue(messagesList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }

    public void setUserOnline(Boolean isOnline) {
        refUsers.child(currentUserId).child("online").setValue(isOnline);
    }

    public LiveData<List<Message>> getMessages() {
        return messages;
    }

    public LiveData<User> getOtherUser() {
        return otherUser;
    }

    public LiveData<Boolean> getMessageSent() {
        return messageSent;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void sendMessage(Message message) {
        refMessages
                .child(message.getSenderId()) // создается пара ключ-массив с ключем senderId
                .child(message.getReceivedId()) // в массив коллекции выше добавляется пара ключ-массив с ключом receivedId
                .push() // в массив коллекции выше добавлется пара ключ-значение с уникальным ключем который сгенерируется автоматически
                .setValue(message) // в значение коллекции выше устанавливается message
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        refMessages
                                .child(message.getReceivedId()) // создается пара ключ-массив с ключем receivedId
                                .child(message.getSenderId()) // в массив коллекции выше добавляется пара ключ-массив с ключом senderId
                                .push() // в массив коллекции выше добавлется пара ключ-значение с уникальным ключем который сгенерируется автоматически
                                .setValue(message) // в значение коллекции выше устанавливается message
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        messageSent.setValue(true);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        error.setValue(e.getMessage());
                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        error.setValue(e.getMessage());
                    }
                });
    }
}
