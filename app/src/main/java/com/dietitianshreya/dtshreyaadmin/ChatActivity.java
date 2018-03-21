package com.dietitianshreya.dtshreyaadmin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.dietitianshreya.dtshreyaadmin.adapters.ChatAdapter;
import com.dietitianshreya.dtshreyaadmin.models.ChatModel;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ChatModel> chatList;
    ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView = (RecyclerView) findViewById(R.id.re);
        chatList=new ArrayList<>();
        chatAdapter = new ChatAdapter(chatList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(chatAdapter);
        chatList.add(new ChatModel("Hello","Customer"));
        chatList.add(new ChatModel("How are you","Customer"));
        chatList.add(new ChatModel("I'm Good","Doctor"));
        chatList.add(new ChatModel("Hello","Customer"));
        chatList.add(new ChatModel("How are you","Customer"));
        chatList.add(new ChatModel("I'm Good","Doctor"));
        chatList.add(new ChatModel("Hello","Customer"));
        chatList.add(new ChatModel("How are you","Customer"));
        chatList.add(new ChatModel("I'm Good","Doctor"));
        chatAdapter.notifyDataSetChanged();

    }

}
