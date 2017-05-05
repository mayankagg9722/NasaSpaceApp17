package com.example.mayankaggarwal.nasago;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class ChartActivity extends AppCompatActivity {

    Spinner spinner;
    private LineChart mChart;
    TextView population,growth;
    ImageView up,down;
    CardView results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_chart);

        spinner=(Spinner)findViewById(R.id.spinner);
        growth=(TextView)findViewById(R.id.growthtext);
        population=(TextView)findViewById(R.id.populationtext);
        down=(ImageView)findViewById(R.id.down);
        up=(ImageView)findViewById(R.id.up);
        results=(CardView)findViewById(R.id.results);

        results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        population.setText(String.valueOf(Globals.population));
        int l=String.valueOf(Float.parseFloat(Globals.value)*1000).length();
        growth.setText(String.valueOf(Float.parseFloat(Globals.value)*1000).substring(0,l-2));

        if((Float.parseFloat(Globals.value)*1000)>500){
            down.setColorFilter(ContextCompat.getColor(ChartActivity.this,R.color.green));
            up.setColorFilter(ContextCompat.getColor(ChartActivity.this,R.color.brown));
        }else {
            up.setColorFilter(ContextCompat.getColor(ChartActivity.this,R.color.green));
            down.setColorFilter(ContextCompat.getColor(ChartActivity.this,R.color.brown));
        }

        final List<String> list=new ArrayList<String>();
        list.add("2017");
        list.add("2018");
        list.add("2019");
        list.add("2020");
        list.add("2021");
        list.add("2022");


        ArrayAdapter<String> adp1=new ArrayAdapter<String>(this,
                R.layout.spinneritem,list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adp1);

        mChart = (LineChart) findViewById(R.id.chart1);
        mChart.setViewPortOffsets(0, 0, 0, 0);
        mChart.setBackgroundColor(Color.rgb(107, 94, 94));

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        mChart.setMaxHighlightDistance(300);

        XAxis x = mChart.getXAxis();
        x.setEnabled(false);

        YAxis y = mChart.getAxisLeft();
//        y.setTypeface(mTfLight);
        y.setLabelCount(6, false);
        y.setTextColor(Color.WHITE);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(false);
        y.setAxisLineColor(Color.WHITE);

        mChart.getAxisRight().setEnabled(false);

        // add data

//        Log.d("play",Globals.lat);
//        Log.d("play", String.valueOf(Globals.latdouble));
        int start= (int) Math.floor(Globals.latdouble);

//        Log.d("tagg",String.valueOf(start));

        setData(start,start+45);

        mChart.getLegend().setEnabled(false);

        mChart.animateXY(2000, 2000);

        mChart.invalidate();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item= (String) parent.getAdapter().getItem(position);
                Log.d("tagg",item);
                Globals.latdouble=Globals.latdouble+(position+1)*20;
                int start= (int) Math.floor(Globals.latdouble);
                setData(start-(position+1)*20,start+45);
                mChart.getLegend().setEnabled(false);

                mChart.animateXY(2000, 2000);

                mChart.invalidate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }
    private void setData(int count, float range) {

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult) + 20;// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals.add(new Entry(i, val));
        }

        LineDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet)mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals, "DataSet 1");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            //set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(4f);
            set1.setCircleColor(Color.WHITE);
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(Color.WHITE);
            set1.setFillColor(Color.WHITE);
            set1.setFillAlpha(100);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return -10;
                }
            });

            // create a data object with the datasets
            LineData data = new LineData(set1);
//            data.setValueTypeface(mTfLight);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            mChart.setData(data);
        }
    }
}
