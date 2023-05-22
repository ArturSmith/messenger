package com.example.messenger;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordViewModel extends AndroidViewModel {

    private FirebaseAuth auth;
    public MutableLiveData<Boolean> success = new MutableLiveData<>();

    public ForgotPasswordViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
    }

    public void resetPassword(String email, Context context) {
        auth.sendPasswordResetEmail(email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Email is sent", Toast.LENGTH_SHORT).show();
                        success.setValue(true);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Wrong email", Toast.LENGTH_SHORT).show();
                        success.setValue(false);
                    }
                });
    }
}
