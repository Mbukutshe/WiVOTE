package com.example.mczondi.wivote;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Jack on 5/18/2016.
 */
public class MessageDataSource {
    private static final Firebase fReff = new Firebase("https://sweltering-heat-8025.firebaseio.com/condition");
    private static SimpleDateFormat dateFomat = new SimpleDateFormat("hh:mm a");
    private static final String TAG="MessageDataSource";
    private static final String CulumumnText="text";
    private static final String CulumnSender="Sender";

    public static void  saveMessage(Message message,String convoId,String sender)
    {
        Date date=message.getmDate();
        String key=dateFomat.format(date);
        HashMap<String,String> msg=new HashMap<>();
        msg.put(CulumumnText,message.getmText());
        msg.put(CulumnSender,sender);
        fReff.child(convoId).child(key).setValue(msg);
    }

    public static MessageListener addMessageListener ( String mConvoId, final MessageCallbacks callbacks ) {
        MessageListener listener=new MessageListener(callbacks);
        fReff.child(mConvoId).addChildEventListener(listener);
        return listener;
    }

    public static void stop(MessageListener listener){
        fReff.removeEventListener(listener);
    }
    public  static  class  MessageListener implements ChildEventListener {

        private MessageCallbacks callbacks;


        public MessageListener(MessageCallbacks callbacks) {
            this.callbacks = callbacks;
        }


        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap<String,String> msg= (HashMap<String, String>) dataSnapshot.getValue();
            Message message=new Message();
            message.setmSender(msg.get(CulumnSender));
            message.setmText(msg.get(CulumumnText));

            try {
                message.setmDate(dateFomat.parse(dataSnapshot.getKey()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(callbacks!=null)
            {
                callbacks.onMessageAdded(message);
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }



    public interface  MessageCallbacks{
        public void onMessageAdded(Message message);
    }

}