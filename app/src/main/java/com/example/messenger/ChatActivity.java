package com.example.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private static final String EXTRA_CURRENT_USER_ID = "current_user";
    private static final String EXTRA_OTHER_USER_ID = "other_user";

    private TextView title;
    private View onlineStatus;
    private RecyclerView recyclerView;
    private EditText editTextMessage;
    private ImageView sendMessageImage;
    private MessageAdapter adapter;
    private String currentUserId;
    private String otherUserID;
    private ChatViewModel viewModel;
    private ChatViewModelFactory chatViewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initViews();
        observeViewModel();
        listeners();




    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.setUserOnline(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.setUserOnline(false);
    }

    private void listeners() {
        sendMessageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new Message(
                        editTextMessage.getText().toString().trim(),
                        currentUserId,
                        otherUserID);
                viewModel.sendMessage(message);
            }
        });
    }

    private void observeViewModel() {
        viewModel.getMessages().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                adapter.setMessages(messages);
            }
        });

        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if (error != null) {
                    Toast.makeText(ChatActivity.this, error, Toast.LENGTH_SHORT);
                }
            }
        });

        viewModel.getMessageSent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean sent) {
                if (sent) {
                    editTextMessage.setText("");
                }
            }
        });

        viewModel.getOtherUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                String userInfo = String.format("%s %s", user.getName(), user.getLastName());
                title.setText(userInfo);

                int bgResId;
                if (user.isOnline()) {
                    bgResId = R.drawable.circle_green;
                } else {
                    bgResId = R.drawable.circle_red;
                }
                Drawable background = ContextCompat.getDrawable(ChatActivity.this, bgResId);
                onlineStatus.setBackground(background);
            }
        });
    }

    private void initViews() {
        title = findViewById(R.id.textViewTitleChA);
        onlineStatus = findViewById(R.id.viewOnlineStatusChA);
        recyclerView = findViewById(R.id.recyclerViewChA);
        editTextMessage = findViewById(R.id.editTextMessageChA);
        sendMessageImage = findViewById(R.id.imageViewSendMessageChA);
        currentUserId = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);
        otherUserID = getIntent().getStringExtra(EXTRA_OTHER_USER_ID);
        chatViewModelFactory = new ChatViewModelFactory(currentUserId, otherUserID);
        viewModel = new ViewModelProvider(this, chatViewModelFactory).get(ChatViewModel.class);

        adapter = new MessageAdapter(currentUserId);
        recyclerView.setAdapter(adapter);
    }

    public static Intent newIntent(Context context, String currentUserID, String otherUserID) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserID);
        intent.putExtra(EXTRA_OTHER_USER_ID, otherUserID);
        return intent;
    }
}