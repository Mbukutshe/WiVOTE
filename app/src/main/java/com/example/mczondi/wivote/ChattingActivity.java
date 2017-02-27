package com.example.mczondi.wivote;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChattingActivity extends ActionBarActivity implements View.OnClickListener,MessageDataSource.MessageCallbacks {
    private ArrayList<Message> mMessages;
    private MessageAdapter mAdapter;

    private String userName;
    private ListView mListView;
    private String mConvoId;
    public String organization;
    String insertUrl ="http://wiseman.cloudaccess.host/notification.php";
    RequestQueue requestQueue;
    private MessageDataSource.MessageListener nListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Firebase.setAndroidContext(this);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        organization = getIntent().getExtras().getString("organization");
        userName="sender";

        mListView=(ListView) findViewById(R.id.messages_List);
        mMessages= new ArrayList<>();
        mAdapter= new MessageAdapter(mMessages);
        mListView.setAdapter(mAdapter);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true
            );
        }
        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText newMessageView = (EditText)findViewById(R.id.messageBodyField);
                final String newMessage=newMessageView.getText().toString();
                newMessageView.setText("");
                Message msg= new Message();
                msg.setmDate(new Date());
                msg.setmText(newMessage);
                msg.setmSender(userName);
                MessageDataSource.saveMessage(msg, mConvoId,msg.getmSender());
            }
        });
        //ImageButton sendMessage=(ImageButton) findViewById(R.id.sendButton);
       // sendMessage.setOnClickListener(this);
        if(organization.equals("SASCO"))
        {
            mConvoId="Vote";
        }
        else
            if(organization.equals("DASO"))
            {
                mConvoId="DASO";
            }
             else
                if(organization.equals("EFFSC"))
                {
                    mConvoId="EFFSC";
                }
                else
                    if(organization.equals("NASMO"))
                    {
                        mConvoId="NASMO";
                    }
                    else
                        if(organization.equals("SADESMO"))
                        {
                            mConvoId="SADESMO";
                        }
        nListener=MessageDataSource.addMessageListener(mConvoId,this);

    }

    @Override
    public void onClick(View v) {


    }

    @Override
    public void onMessageAdded(Message message) {
        mMessages.add(message);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        MessageDataSource.stop(nListener);
    }

    private class MessageAdapter extends ArrayAdapter<Message> {
        MessageAdapter(ArrayList<Message>messages){
            super(ChattingActivity.this, R.layout.message_item, R.id.message, messages);
        }
        @Override
        public View getView(int possition,View convertView, ViewGroup parent){
            convertView=super.getView(possition,convertView,parent);
            Message message=getItem(possition);
            TextView nameView=(TextView) convertView.findViewById(R.id.message);
            nameView.setText(message.getmText());

            LinearLayout.LayoutParams layoutParams=(LinearLayout.LayoutParams)nameView.getLayoutParams();
            if(message.getmSender().equals(userName))
            {
                nameView.setBackgroundResource(R.drawable.vm0sa);
                nameView.setLayoutParams(layoutParams);
                layoutParams.gravity= Gravity.RIGHT;
            }else {

                nameView.setBackgroundResource(R.drawable.vm0sa);
                nameView.setLayoutParams(layoutParams);
                layoutParams.gravity= Gravity.LEFT;
            }
            nameView.setLayoutParams(layoutParams);

            return  convertView;
        }

    }

}

