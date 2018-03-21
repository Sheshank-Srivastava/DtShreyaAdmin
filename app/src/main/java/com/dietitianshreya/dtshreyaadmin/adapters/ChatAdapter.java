package com.dietitianshreya.dtshreyaadmin.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.dietitianshreya.dtshreyaadmin.R;
import com.dietitianshreya.dtshreyaadmin.models.ChatModel;

import java.util.ArrayList;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private ArrayList<ChatModel> chatList;


    public ChatAdapter(ArrayList<ChatModel> chatList) {
        this.chatList = chatList;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView chatText;
        public LinearLayout chatBackground;
        public MyViewHolder(View view) {
            super(view);
            chatText = (TextView) view.findViewById(R.id.chatText);
            chatBackground= (LinearLayout) view.findViewById(R.id.chatBackground);
        }
    }


    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_list_row, parent, false);

        return new ChatAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChatAdapter.MyViewHolder holder, int position) {


        ChatModel chatModel = chatList.get(position);
        Log.d("My tag",chatModel.getMessage());
        holder.chatText.setText(chatModel.getMessage());
        if(chatModel.getSender().equals("Customer")){
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)) ;
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.setMargins(100,2,5,0);
            holder.chatText.setTextColor(Color.parseColor("#ffffff"));
            holder.chatText.setTextSize(16);
            holder.chatBackground.setLayoutParams(params);
            holder.chatBackground.setBackgroundResource(R.drawable.chat_sender_back);
        }
        else{
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)) ;
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            if(chatModel.getMessage().equalsIgnoreCase("Answer awaited")){
                holder.chatText.setTextColor(Color.parseColor("#9e9e9e"));
                holder.chatText.setTextSize(14);
            }
            else{
                holder.chatText.setTextColor(Color.parseColor("#000000"));
                holder.chatText.setTextSize(16);
            }
            params.setMargins(5,2,100,0);
            holder.chatBackground.setLayoutParams(params);
            holder.chatBackground.setBackgroundResource(R.drawable.chat_reciever_back);
        }

    }

    @Override
    public int getItemCount()
    {
        return chatList.size();
    }



}