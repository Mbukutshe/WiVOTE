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
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity implements AsyncResponse,View.OnClickListener {

    private Button change_pass;
    private EditText Q1,Q2;
    private EditText Q3;
    private EditText pass1;
    private EditText pass2;
    private EditText  password;
    private static final String URL = "http://wiseman.cloudaccess.host/forgot.php";
    private RequestQueue requestQueue;
    private StringRequest request;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        change_pass = (Button)findViewById(R.id.btn_signup);
        Q1 = (EditText)findViewById(R.id.studentNo);
        Q2 = (EditText)findViewById(R.id.input_name);
        Q3 = (EditText)findViewById(R.id.input_email);
        pass1 = (EditText)findViewById(R.id.input_password);
        pass2 = (EditText)findViewById(R.id.confirm_password);
        change_pass.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        if((pass1.getText().toString()).equals(pass2.getText().toString())){

            HashMap postData = new HashMap();
            postData.put("mobile", "android");
            postData.put("txtStdnum", Q1.getText().toString());
            postData.put("txtFirstname", Q2.getText().toString());
            postData.put("txtSurname", Q3.getText().toString());
            postData.put("txtPassword", pass1.getText().toString());
            PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
            task.execute("http://wiseman.cloudaccess.host/forgot.php");
        }
       else {
           Toast.makeText(this, "Passwords do not Match", Toast.LENGTH_LONG).show();
        }

    }
    public void processFinish(String result) {

        if (result.equals("Success")) {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        } else {
            Toast.makeText(this, result.toString(), Toast.LENGTH_LONG).show();
        }

    }

}