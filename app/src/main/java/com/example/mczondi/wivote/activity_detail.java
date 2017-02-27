package com.example.mczondi.wivote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by MC ZONDI on 6/7/2016.
 */
public class activity_detail extends AppCompatActivity {

    public static final String EXTRA_NAME = "cheese_name";
    public int pos;
    public String organization;
    TextView manif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        pos = getIntent().getExtras().getInt("position_on_list");
        organization = getIntent().getExtras().getString("organization");
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        manif = (TextView)findViewById(R.id.mani);
        //  setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        switch (pos)
        {
            case 0:
                manif.setText("The Provision of Free and Quality Education for all MUT Students by 2017 (Free Education for Final Year MUT NSFAS Students).");
                break;
            case 1:
                manif.setText("The severe economic and social consequences of being affected by crime are serious, and can range from being unable to attend lectures due to physical injuries, high levels of post-traumatic stress which impacts negatively on studies, as well as the direct financial costs.");
                break;
            case 2:
                manif.setText("The current centralization of NSFAS is not properly structured thus disadvantaging\n" +
                        "students from poor backgrounds. We totally reject the distancing of MUT from\n" +
                        "providing services of NSFAS to our students. Facilities such as telephone, computers and\n" +
                        "NSFAS administrators should be provided to our student’s .EFF will robustly engage\n" +
                        "management of the institution to deal with this matter as speedily as possible.");
                break;
            case 3:
                manif.setText("Opening of the university 24 hours during examination time\n" +
                        "o Water tapes all over the campus of MUT\n" +
                        "o Application of national students financial aid schemes [NSFAS] should be submitter online and supporting document\n" +
                        "o National Students Financial Aid Schemes should cover for the full academic year.");
                break;
            case 4:
                manif.setText("Students Representative Committee [SRC] period should be one year.\n" +
                        "o Budget must be distributed to every organisation that is existing within the university including religious organisation.\n" +
                        "o We should have magic tour.\n" +

                        "o University need to provide students with laboratory");
                break;
        }

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("");

        loadBackdrop();
        FloatingActionButton comments = (FloatingActionButton)findViewById(R.id.floating);
        comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context,ChattingActivity.class);
                intent.putExtra("organization",organization);
                context.startActivity(intent);
            }
        });
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(Org.getOrganizastion(pos)).centerCrop().into(imageView);
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
}
