package com.example.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initViews();
        currentUserId = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);
        otherUserID = getIntent().getStringExtra(EXTRA_OTHER_USER_ID);
        adapter = new MessageAdapter(currentUserId);

        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
         Message message = new Message(
                 "Text" + i,
                 currentUserId,
                 otherUserID
         );
         messages.add(message);
        }

        for (int i = 0; i < 10; i++) {
            Message message = new Message(
                    "Text" + i,
                    otherUserID,
                    currentUserId
            );
            messages.add(message);
        }
        adapter.setMessages(messages);
        recyclerView.setAdapter(adapter);
    }

    private void initViews() {
        title = findViewById(R.id.textViewTitleChA);
        onlineStatus = findViewById(R.id.viewOnlineStatusChA);
        recyclerView = findViewById(R.id.recyclerViewChA);
        editTextMessage = findViewById(R.id.editTextMessageChA);
        sendMessageImage = findViewById(R.id.imageViewSendMessageChA);
    }

    public static Intent newIntent(Context context, String currentUserID, String otherUserID) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserID);
        intent.putExtra(EXTRA_OTHER_USER_ID, otherUserID);
        return intent;
    }
}