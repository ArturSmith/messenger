package com.example.messenger.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.messenger.domain.Message;
import com.example.messenger.domain.MessengerRepository;
import com.example.messenger.domain.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MessengerRepositoryImpl implements MessengerRepository {

    private final FirebaseAuth auth;
    private final DatabaseReference databaseReferenceUsers;
    private final DatabaseReference databaseReferenceMessages;
    private final MutableLiveData<FirebaseUser> userFromFirebase = new MutableLiveData<>();
    private final MutableLiveData<User> otherUser = new MutableLiveData<>();
    private final MutableLiveData<List<User>> users = new MutableLiveData<>();
    private final MutableLiveData<List<Message>> messages = new MutableLiveData<>();

    public MessengerRepositoryImpl() {
        auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReferenceUsers = database.getReference("Users");
        databaseReferenceMessages = database.getReference("Messages");
    }

    @Override
    public void login(String email, String password, MutableLiveData<String> errorOfSignIn) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        userFromFirebase.setValue(authResult.getUser());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        errorOfSignIn.setValue(e.toString());
                    }
                });
    }

    @Override
    public LiveData<FirebaseUser> getUserFromFirebase() {
        return userFromFirebase;
    }


    @Override
    public void getCurrentUser() {
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                userFromFirebase.setValue(firebaseAuth.getCurrentUser());
            }
        });
    }

    @Override
    public void loadUsers() {
        databaseReferenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseUser currentUser = auth.getCurrentUser();

                if (currentUser == null) return;

                List<User> usersFromDB = new ArrayList<>();

                for (DataSnapshot dataSnapshot :
                        snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user == null) return;
                    if (!user.getId().equals(currentUser.getUid())) {
                        usersFromDB.add(user);
                    }
                }
                users.setValue(usersFromDB);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void setCurrentUserOnline(Boolean isOnline) {
        if (auth.getCurrentUser() == null) return;
        databaseReferenceUsers.child(auth.getCurrentUser().getUid()).child("online").setValue(isOnline);
    }

    @Override
    public LiveData<User> getOtherUser(String otherUserId) {
        databaseReferenceUsers.child(otherUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                otherUser.setValue(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return otherUser;
    }

    @Override
    public LiveData<List<Message>> getMessages(String currentUserId, String otherUserId) {
        databaseReferenceMessages.child(currentUserId).child(otherUserId)
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
        return messages;
    }

    @Override
    public void sendMessage(Message message, MutableLiveData<String> error, MutableLiveData<Boolean> messageSent) {
        databaseReferenceMessages
                .child(message.getSenderId()) // создается пара ключ-массив с ключем senderId
                .child(message.getReceivedId()) // в массив коллекции выше добавляется пара ключ-массив с ключом receivedId
                .push() // в массив коллекции выше добавлется пара ключ-значение с уникальным ключем который сгенерируется автоматически
                .setValue(message) // в значение коллекции выше устанавливается message
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        databaseReferenceMessages
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

    @Override
    public void signOut() {
        setCurrentUserOnline(false);
        auth.signOut();
    }

    @Override
    public LiveData<List<User>> getUsers() {
        return users;
    }

    @Override
    public void resetPassword(String email, MutableLiveData<String> error, MutableLiveData<Boolean> success) {
        auth.sendPasswordResetEmail(email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        success.setValue(true);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        error.setValue(e.toString());
                    }
                });
    }

    @Override
    public void signup(String email, String password, String name, String lastName, MutableLiveData<String> error) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser firebaseUser = authResult.getUser();
                    if (firebaseUser == null) return;
                    User user = new User(
                            firebaseUser.getUid(),
                            name,
                            lastName,
                            false
                    );
                    databaseReferenceUsers.child(user.getId()).setValue(user);
                })
                .addOnFailureListener(e -> {
                    error.setValue(e.toString());
                });
    }

    @Override
    public void setUserOnline(String userId, Boolean isOnline) {
        databaseReferenceUsers.child(userId).child("online").setValue(isOnline);
    }


}
