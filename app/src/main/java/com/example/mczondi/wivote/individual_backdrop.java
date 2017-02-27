package com.example.mczondi.wivote;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MC ZONDI on 6/26/2016.
 */
public class individual_backdrop extends AppCompatActivity {

    public static final String EXTRA_NAME = "cheese_name";
    public static int pos;
    public String stdnum;
    private List<Salons> salons;
    String json_string;
    JSONArray jsonArray;
    JSONObject jsonObject;
    public String candidateName;
    String insertUrl ="http://wiseman.cloudaccess.host/individualVotes.php";
    RequestQueue requestQueue;
    TextView candidate_name,candidate_votes;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_backdrop);
        candidate_name = (TextView) findViewById(R.id.Votes);
        candidate_votes = (TextView) findViewById(R.id.candidateVotes);
        pos = getIntent().getExtras().getInt("position");
        stdnum = getIntent().getExtras().getString("stdnum");
        requestQueue = Volley.newRequestQueue(this);
        getJSON();
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        switch (pos)
        {
            case 0:
                collapsingToolbar.setTitle("Zondi Mduduzi");
                candidateName = "mdu";
                break;
            case 1:
                collapsingToolbar.setTitle("Wiseman Khanyisa");
                candidateName = "wisy";
                break;
            case 2:
                collapsingToolbar.setTitle("Njabulo Zulu");
                candidateName = "njiva";
                break;
            case 3:
                collapsingToolbar.setTitle("Ngidi Sfundo");
                candidateName = "sfundo";
                break;
            case 4:
                collapsingToolbar.setTitle("Jack Pero");
                candidateName = "jack";
                break;
        }


        loadBackdrop();
        FloatingActionButton comments = (FloatingActionButton)findViewById(R.id.floating);
        comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String,String>();
                        parameters.put("stdnum",stdnum);

                        if(pos == 0)
                        {
                            parameters.put("candidate", "mdu");
                        }
                        else
                        if(pos==1)
                        {
                            parameters.put("candidate", "wisy");
                        }
                        else
                        if(pos==2)
                        {
                            parameters.put("candidate", "njiva");
                        }
                        else
                        if(pos==3)
                        {
                            parameters.put("candidate", "sfundo");
                        }
                        else
                        if(pos==4)
                        {
                            parameters.put("candidate", "jack");
                        }

                        return parameters;
                    }
                };
                requestQueue.add(request);
                getJSON();
            }
        });
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(Org.getIndividual(pos)).centerCrop().into(imageView);
        //Intent intent = new Intent(this,activity_detail.class);
        //intent.putExtra("position_on_list",position);
        //Resources res = getResources();

        //TypedArray ar = res.obtainTypedArray(R.array.places_picture);
        //final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        //imageView.setImageResource(ar.getResourceId(position, -1));
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }
    public void getJSON(){
        new BackgroundTask().execute();
    }
   /* public void parseJSON(View view)
    {

    }*/

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String json_url;

        @Override
        protected void onPreExecute() {
            json_url = "http://wiseman.cloudaccess.host/individual_results.php";
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((json_string = bufferedReader.readLine()) != null) {
                    stringBuilder.append(json_string + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String results) {
            String totalVotes ="";
            try {
                json_string = results.toString();
                jsonObject = new JSONObject(json_string);
                jsonArray = jsonObject.getJSONArray("server_response");
                int count =0;
                String votes,candidate;
                int id;
                while(count<jsonArray.length())
                {
                    JSONObject job = jsonArray.getJSONObject(count);
                    votes =  job.getString("votes");
                    id = job.getInt("id");
                    candidate = job.getString("candidate");
                    if(candidate.equals(candidateName)) {
                        switch (id - 1) {
                            case 0:
                                totalVotes = "" + votes;
                                break;
                            case 1:
                                totalVotes = "" + votes;
                                break;
                            case 2:
                                totalVotes = "" + votes;
                                break;
                            case 3:
                                totalVotes = "" + votes;
                                break;
                            case 4:
                                totalVotes = "" + votes;
                                break;
                        }
                    }
                    candidate_votes.setText(totalVotes);
                    count++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
