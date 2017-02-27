package com.example.mczondi.wivote;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.telerik.widget.chart.engine.dataPoints.DataPoint;
import com.telerik.widget.chart.engine.databinding.DataPointBinding;
import com.telerik.widget.chart.engine.databinding.PropertyNameDataPointBinding;
import com.telerik.widget.chart.visualization.behaviors.ChartSelectionBehavior;
import com.telerik.widget.chart.visualization.behaviors.ChartSelectionChangeListener;
import com.telerik.widget.chart.visualization.behaviors.ChartSelectionContext;
import com.telerik.widget.chart.visualization.behaviors.ChartSelectionMode;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.LineSeries;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.SphericalDataPointIndicatorRenderer;
import com.telerik.widget.chart.visualization.pieChart.PieSeries;
import com.telerik.widget.chart.visualization.pieChart.RadPieChartView;
import com.telerik.widget.primitives.legend.RadLegendView;

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
import java.util.ArrayList;
import java.util.List;
/**
 * Created by MC ZONDI on 6/2/2016.
 */
public class wi_fragment_three extends Fragment {
    public List<Salons> salons;
    public static String json_string;
    JSONArray jsonArray;
    JSONObject jsonObject;
    View view;
    TextView sasco,daso,effsc,nasmo,sadesmo;
    SwipeRefreshLayout refreshLayout;
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        super.onCreateView(inflater,container,savedInstance);
        view = inflater.inflate(R.layout.fragment_three,container,false);
        sasco = (TextView) view.findViewById(R.id.sasco);
        daso = (TextView) view.findViewById(R.id.daso);
        effsc = (TextView) view.findViewById(R.id.effsc);
        nasmo = (TextView) view.findViewById(R.id.nasmo);
        sadesmo = (TextView) view.findViewById(R.id.sadesmo);
        getJSON();
        return view;
    }
    private void InitData(String idNo ,double stdNum) {

        salons.add(new Salons(idNo,stdNum));
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
            json_url = "http://wiseman.cloudaccess.host/org_results.php";
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
            wi_fragment_three.json_string = results;
            salons = new ArrayList();
            try {
                jsonObject = new JSONObject(json_string);
                jsonArray = jsonObject.getJSONArray("server_response");
                int count =0;
                String bookers,salon_name;
                while(count<jsonArray.length())
                {
                    JSONObject job = jsonArray.getJSONObject(count);
                    bookers =  job.getString("voters");
                    salon_name = job.getString("organizations");
                    if(salon_name.equals("SASCO"))
                    {
                        sasco.setText(salon_name+":"+bookers);
                        sasco.setTextColor(getResources().getColor(R.color.sasco));
                    }
                    else
                        if(salon_name.equals("DASO"))
                        {
                            daso.setText(salon_name+":"+bookers);
                            daso.setTextColor(getResources().getColor(R.color.daso));
                        }
                        else
                            if(salon_name.equals("EFFSC"))
                            {
                                effsc.setText(salon_name+":"+bookers);
                                effsc.setTextColor(getResources().getColor(R.color.effsc));
                            }
                            else
                                if(salon_name.equals("NASMO"))
                                {
                                    nasmo.setText(salon_name+":"+bookers);
                                    nasmo.setTextColor(getResources().getColor(R.color.nasmo));
                                }
                                else
                                    if(salon_name.equals("SADESMO"))
                                    {
                                        sadesmo.setText(salon_name+":"+bookers);
                                        sadesmo.setTextColor(getResources().getColor(R.color.sadesmo));
                                    }
                    InitData(salon_name,Double.parseDouble(bookers));
                    count++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RadPieChartView pieView = new RadPieChartView(getContext());
            PieSeries pieSeries = new PieSeries();
            pieSeries.setShowLabels(true);
            pieSeries.setValueBinding(new DataPointBinding() {
                @Override
                public Object getValue(Object o) throws IllegalArgumentException {
                    return ((Salons) o).getNumber();
                }
            });
            pieSeries.setData(salons);
            pieView.getSeries().add(pieSeries);
            FrameLayout rootView = (FrameLayout)view.findViewById(R.id.line_graph);
            pieSeries.setIsVisibleInLegend(true);

            rootView.addView(pieView);
        }
    }
}
