package com.example.mczondi.wivote;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.support.v4.app.Fragment;


import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MC ZONDI on 6/2/2016.

 */
public class wi_fragment extends android.support.v4.app.Fragment {

    public static int pos;
    public static String stdnum;
    public static String name1;
    public static String organization;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
          ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        stdnum = getArguments().getString("student");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return recyclerView;



    }
   // public static int position = 0;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public TextView description;
        String insertUrl ="http://wiseman.cloudaccess.host/book.php";
        RequestQueue requestQueue;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.fragment_one, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.card_image);
            name = (TextView) itemView.findViewById(R.id.card_title);
            description = (TextView) itemView.findViewById(R.id.card_text);
            requestQueue = Volley.newRequestQueue(inflater.getContext());
            // Adding Snackbar to Action Button inside card
            /*Button button = (Button)itemView.findViewById(R.id.action_button);
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Action is pressed",
                            Snackbar.LENGTH_LONG).show();
                }
            });*/

            ImageButton favoriteImageButton =
                    (ImageButton) itemView.findViewById(R.id.favorite_button);
            favoriteImageButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(final View v) {
                    StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                        Toast.makeText(v.getContext(),response,Toast.LENGTH_LONG).show();
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
                            pos = getAdapterPosition();

                            if(pos == 0)
                            {
                                parameters.put("organization", "SASCO");
                            }
                            else
                            if(pos==1)
                            {
                                parameters.put("organization", "DASO");
                            }
                            else
                            if(pos==2)
                            {
                                parameters.put("organization", "EFFSC");
                            }
                            else
                            if(pos==3)
                            {
                                parameters.put("organization", "NASMO");
                            }
                            else
                            if(pos==4)
                            {
                                parameters.put("organization", "SADESMO");
                            }

                            return parameters;
                        }
                    };
                    requestQueue.add(request);
                }
            });



            ImageButton shareImageButton = (ImageButton) itemView.findViewById(R.id.share_button);
            shareImageButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    pos = getAdapterPosition();
                    switch (pos)
                    {
                        case 0:
                            organization = "SASCO";
                            break;
                        case 1:
                            organization = "DASO";
                            break;
                        case 2:
                            organization = "EFFSC";
                            break;
                        case 3:
                            organization = "NASMO";
                            break;
                        case 4:
                            organization = "SADESMO";
                            break;
                    }

                    //Snackbar.make(v, "Share article",
                      //      Snackbar.LENGTH_LONG).show();
                   // Intent intent = new Intent(activity_detail.class);
                    Context context = v.getContext();
                    Intent intent = new Intent(context,activity_detail.class);
                    intent.putExtra("position_on_list",pos);
                    intent.putExtra("organization",organization);
                    context.startActivity(intent);
                }
            });

        }
    }

    /**
     * Adapter to display recycler view.
     */
  public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of Card in RecyclerView.
        private static final int LENGTH = 5;

        private final String[] mPlaces;
        private final String[] mPlaceDesc;
        private final Drawable[] mPlacePictures;

       public ContentAdapter(Context context) {
            Resources resources = context.getResources();
            mPlaces = resources.getStringArray(R.array.places);
            mPlaceDesc = resources.getStringArray(R.array.place_desc);
            TypedArray a = resources.obtainTypedArray(R.array.places_picture);
            mPlacePictures = new Drawable[a.length()];
            for (int i = 0; i < mPlacePictures.length; i++) {
                mPlacePictures[i] = a.getDrawable(i);
            }
            a.recycle();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int  position) {

            holder.picture.setImageDrawable(mPlacePictures[position % mPlacePictures.length]);
            holder.name.setText(mPlaces[position % mPlaces.length]);
            holder.description.setText(mPlaceDesc[position % mPlaceDesc.length]);
            name1 = holder.name.getText().toString();

        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }


}
