package com.example.messenger;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;

public class HomeViewModel extends AndroidViewModel {

    private FirebaseAuth auth;
    public MutableLiveData<Boolean> loggedOut = new MutableLiveData<>();
    public HomeViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
    }

    public void logout(){
        auth.signOut();
        loggedOut.setValue(true);
    }
}
