package com.example.mczondi.wivote;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.w3c.dom.Text;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;



public class LoginActivity extends AppCompatActivity implements AsyncResponse,View.OnClickListener {

    private EditText email, password;
    private Button sign_in_register;
    private TextView forgot;
    public static String stdnum;
    private static final String URL = "http://wiseman.cloudaccess.host/VoteLogin.php";
    private RequestQueue requestQueue;
    private StringRequest request;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        FirebaseMessaging.getInstance().subscribeToTopic("test");
        FirebaseInstanceId.getInstance().getToken();
        email = (EditText)findViewById(R.id.input_email);
        password = (EditText)findViewById(R.id.input_password);
        sign_in_register = (Button)findViewById(R.id.btn_login);
        forgot = (TextView) findViewById(R.id.link_signup);
        requestQueue = Volley.newRequestQueue(this);
        sign_in_register.setOnClickListener(this);

        forgot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intent);

            }
        });

    }
    public void onClick(View v) {

      /*  HashMap postData = new HashMap();
        postData.put("mobile", "android");
        stdnum = email.getText().toString();
        postData.put("txtUsername", email.getText().toString());
        postData.put("txtPassword", password.getText().toString());
        PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
        task.execute("http://wiseman.cloudaccess.host/VoteLogin.php");*/
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);


    }
    public void processFinish(String result) {

      /*  if (result.equals("Success"))
        {
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            i.putExtra("student",stdnum);
            startActivity(i);
        }
        else
            {
                Toast.makeText(this, "Incorrect username or password ", Toast.LENGTH_LONG).show();
            }
*/
    }


}
