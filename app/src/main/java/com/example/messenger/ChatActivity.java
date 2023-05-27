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

public class ChatActivity extends AppCompatActivity {

    private TextView title;
    private View onlineStatus;
    private RecyclerView recyclerView;
    private EditText editTextMessage;
    private ImageView sendMessageImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initViews();
    }

    private void initViews(){
        title = findViewById(R.id.textViewTitleChA);
        onlineStatus = findViewById(R.id.viewOnlineStatusChA);
        recyclerView = findViewById(R.id.recyclerViewChA);
        editTextMessage = findViewById(R.id.editTextMessageChA);
        sendMessageImage = findViewById(R.id.imageViewSendMessageChA);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, ChatActivity.class);
    }
}