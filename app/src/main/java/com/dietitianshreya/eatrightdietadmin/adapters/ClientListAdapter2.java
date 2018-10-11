package com.dietitianshreya.eatrightdietadmin.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dietitianshreya.eatrightdietadmin.ChatActivity;
import com.dietitianshreya.eatrightdietadmin.R;
import com.dietitianshreya.eatrightdietadmin.Utils.VariablesModels;
import com.dietitianshreya.eatrightdietadmin.models.ClientListModel2;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.dietitianshreya.eatrightdietadmin.Login.MyPREFERENCES;

public class ClientListAdapter2 extends RecyclerView.Adapter<ClientListAdapter2.MyViewHolder2> {

    private ArrayList<ClientListModel2> clientList;
    private Context mCtx;
    FirebaseDatabase firebaseDatabase =FirebaseDatabase.getInstance();
    DatabaseReference storageReference,listread;
    String userid;
    public ClientListAdapter2(ArrayList<ClientListModel2> clientList, Context mCtx) {
        this.clientList = clientList;
        this.mCtx = mCtx;
        SharedPreferences sharedpreferences1 = mCtx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        userid= String.valueOf(sharedpreferences1.getInt("clientId",0));

    }




    public class MyViewHolder2 extends RecyclerView.ViewHolder {


        public TextView placeholder,clientName,clientId,daysLeft,indicator;
        LinearLayout layout;
        public MyViewHolder2(View view) {
            super(view);
            placeholder = (TextView)  view.findViewById(R.id.placeholder);
            clientName = (TextView)  view.findViewById(R.id.clientName);
            clientId = (TextView)  view.findViewById(R.id.clientId);
            daysLeft = (TextView) view.findViewById(R.id.daysLeft);
            indicator = (TextView) view.findViewById(R.id.indicator);
            layout = (LinearLayout) view.findViewById(R.id.body);
        }
    }


    @Override
    public ClientListAdapter2.MyViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.client_list_row, parent, false);

        return new ClientListAdapter2.MyViewHolder2(itemView);
    }

    @Override
    public void onBindViewHolder(ClientListAdapter2.MyViewHolder2 holder, int position) {

        final ClientListModel2 client = clientList.get(position);
        //holder.placeholder.setText(client.getClientName());
        holder.daysLeft.setText(client.getDaysLeft());
        holder.clientName.setText(client.getClientName());
        holder.clientId.setText(client.getClientId());
        char placeholdertext = client.getClientName().charAt(0);
        holder.placeholder.setText(placeholdertext+"");
        if (!client.isRead()) {
            holder.indicator.setVisibility(View.VISIBLE);
            holder.clientName.setText(Html.fromHtml("<b>" + client.getClientName() + "</b> "));
            holder.clientId.setText(Html.fromHtml("<b>" + client.getClientId() + "</b> "));
            holder.daysLeft.setText(Html.fromHtml("<b>" + client.getDaysLeft() + "</b> "));
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storageReference = firebaseDatabase.getReference().child("overview").child(userid).child(client.getClientId() + "");
                ClientListModel2 mod = new ClientListModel2(client.getClientName(), client.getClientId(),client.getDaysLeft(), true,client.getTimeStamp());
                storageReference.setValue(mod);
                client.setRead(true);
                notifyDataSetChanged();
                Intent i = new Intent(mCtx, ChatActivity.class);
                i.putExtra(VariablesModels.userId,client.getClientId());
                i.putExtra("timeStamp",client.getTimeStamp());
                mCtx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return clientList.size();
    }
}



