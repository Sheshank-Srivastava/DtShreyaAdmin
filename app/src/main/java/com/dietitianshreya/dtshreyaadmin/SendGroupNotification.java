package com.dietitianshreya.dtshreyaadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class SendGroupNotification extends AppCompatActivity {

    EditText notificationbody,notificationTitle;
    TextView label;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_group_notification);
        notificationbody = (EditText) findViewById(R.id.body);
        notificationTitle = (EditText) findViewById(R.id.title);
        label = (TextView) findViewById(R.id.textLabel);
        notificationbody.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                label.setText(charSequence.length()+"/1024");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
