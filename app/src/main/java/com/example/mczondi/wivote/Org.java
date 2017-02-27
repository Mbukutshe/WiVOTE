package com.example.mczondi.wivote;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by MC ZONDI on 6/7/2016.
 */
public class Org extends Activity{



    private static final Random RANDOM = new Random();
    final ImageView imageView = (ImageView) findViewById(R.id.backdrop);

    public static int getOrganizastion(int pos) {
        switch (pos) {

            default:
            case 0:
                return R.drawable.ancyl;
            case 1:
                return R.drawable.daso;
            case 2:
                return R.drawable.effsc;
            case 3:
                return R.drawable.nasmo;
            case 4:
                return R.drawable.sadesmo;

        }



    }
    public static int getIndividual(int pos){

        switch (pos) {

            default:
            case 0:
                return R.drawable.ind_ancylpre;
            case 1:
                return R.drawable.ind_daso;
            case 2:
                return R.drawable.ind_eff;
            case 3:
                return R.drawable.ind_nasmo;
            case 4:
                return R.drawable.ind_sadesmo;

        }
    }

    public static final String[] sCheeseStrings = {"SASCO","DASO","EFFSC","NASMO","SADESMO"};
    public static final String[] individualStrings = {"Zondi Mduduzi","Wiseman Khanyisa","Njabulo Zulu","Ngidi Sfundo","Jack Pero"};
}
