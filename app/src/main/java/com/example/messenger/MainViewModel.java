package com.example.messenger;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainViewModel extends AndroidViewModel {

    private FirebaseAuth auth;
    private FirebaseUser user;

    public MutableLiveData<Boolean> authorized = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }

    public void login(String email, String password, Context context) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(context, "Authorized", Toast.LENGTH_SHORT).show();
                        authorized.setValue(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                        authorized.setValue(false);
                    }
                });
    }


}
