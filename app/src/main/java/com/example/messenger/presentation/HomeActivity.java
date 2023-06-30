package com.example.messenger.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.messenger.R;
import com.example.messenger.domain.User;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String EXTRA_CURRENT_USER_ID = "current_user";
    private HomeViewModel viewModel;
    private MaterialToolbar toolbar;
    private RecyclerView recyclerViewUsers;
    private UsersAdapter adapter;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
        setSupportActionBar(toolbar);
        viewModelObserve();
        setSystemBarColor();


        adapter.setOnUserClickListener(new UsersAdapter.OnUserClickListener() {
            @Override
            public void onUserClick(User user) {
                Intent intent = ChatActivity.newIntent(
                        HomeActivity.this,
                        currentUserId,
                        user.getId());
                startActivity(intent);
            }
        });
        Log.d("Life", "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("Life", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("Life", "onResume");
        viewModel.setUserOnline(true);
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("Life", "onPause");
        viewModel.setUserOnline(false);
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("Life", "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d("Life", "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("Life", "onDestroy");
    }

    private void viewModelObserve() {
        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser == null) {
                    Intent intent = LoginActivity.newIntent(HomeActivity.this);
                    startActivity(intent);
                    finish();
                }
            }
        });

        viewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (users != null) {
                    adapter.setUsers(users);
                }
            }
        });
    }

    private void setSystemBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.GRAY);
    }

    public static Intent newIntent(Context context, String currentUserId) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logoutMenuItem) {
            viewModel.logout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        currentUserId = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        toolbar = findViewById(R.id.toolbar);
        recyclerViewUsers = findViewById(R.id.recyclerViewHA);
        adapter = new UsersAdapter();
        recyclerViewUsers.setAdapter(adapter);
    }


}