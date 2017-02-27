package com.example.mczondi.wivote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
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


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wiseman on 2016-05-20.
 */
public class readData extends AppCompatActivity {
    private List<Salons> salons;
    String json_string;
    JSONArray jsonArray;
    JSONObject jsonObject;
    SwipeRefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        salons = new ArrayList();
        try {
            json_string = getIntent().getExtras().getString("json_data");
            Toast.makeText(getApplicationContext(),json_string,Toast.LENGTH_LONG).show();
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count =0;
            String bookers,salon_name;
            while(count<jsonArray.length())
            {
                JSONObject job = jsonArray.getJSONObject(count);
                bookers =  job.getString("voters");
                salon_name = job.getString("organizations");
                InitData(salon_name,Double.parseDouble(bookers));
                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    private void InitData(String idNo ,double stdNum) {

        salons.add(new Salons(idNo,stdNum));
    }
}
