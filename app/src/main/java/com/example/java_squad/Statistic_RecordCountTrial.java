package com.example.java_squad;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Statistic_RecordCountTrial extends AppCompatActivity {

    ArrayList<Count> trialDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic__record_count_trial);

        //three statistic result for non negative count trials


        //table of mean, median, standard deviation, quartiles
        final TableLayout result;
        TextView Mean_text;
        TextView Median_text;
        TextView Stdev_text;
        TextView LowQua_text;
        TextView UpQua_text;


        double mean;
        double median = 0;
        double stdev;
        double low_quartile= 0;
        double up_quartile = 0;

        //get datalist of count trials from RecordCountTrial
        ArrayList<Count> trialDataList = (ArrayList<Count>)getIntent().getSerializableExtra("DataList_of_C_trials");

        //set trials count to data list as source data to compute mean, median, quartiles, standard deviation
        int data[] = new int[trialDataList.size()];

        for (int i = 0; i < trialDataList.size(); i++) {
            data[i] = trialDataList.get(i).getCount();
        }

        //sort data list to find median, quartiles
        Arrays.sort(data);

        result = (TableLayout)findViewById(R.id.result_table_count);
        Mean_text = (TextView)findViewById(R.id.mean_num_count);
        Median_text = (TextView)findViewById(R.id.median_num_count);
        Stdev_text = (TextView)findViewById(R.id.stdev_num_count);
        LowQua_text = (TextView)findViewById(R.id.low_quartile_num_count);
        UpQua_text = (TextView)findViewById(R.id.up_quartile_num_count);

        DecimalFormat fm = new DecimalFormat("0.####");


        //Get and set Mean
        if(data.length == 0) {
            mean = 0;
        }
        else {
            double sum = 0.0;
            for (int index = 0; index != data.length; ++index) {
                sum += data[index];
            }
            mean = sum / data.length;
        }

        Mean_text.setText(fm.format(mean));


        //Get and set Standard Deviation
        double sum = 0;
        for (int index = 0; index != data.length; ++index) {
            sum += Math.pow(Math.abs(mean - data[index]), 2);
        }
        stdev = Math.sqrt(sum / data.length);
        Stdev_text.setText(fm.format(stdev));



        //Get and set median, first quartile, last quartile.
        int half_size = data.length / 2;
        if (data.length == 0) {
            median = 0;
            low_quartile = 0;
            up_quartile = 0;
        }
        if ((data.length != 0) & (data.length < 4)) {
            median = (data.length % 2 != 0) ? data[half_size] : ( data[half_size] + data[half_size - 1] )/2;
            low_quartile = 0;
            up_quartile = 0;
        }
        if((data.length >= 4) & (data.length % 2 == 0)) {
            median = (data.length % 2 != 0) ? data[half_size] : ( data[half_size] + data[half_size - 1] )/2;
            if (half_size % 2 == 0) {
                low_quartile = (data[(half_size / 2) - 1] + data[half_size / 2] ) / 2;
                up_quartile = (data[half_size+ (half_size/2)-1] + data[half_size + (half_size/2)] ) / 2;
            }
            else {
                low_quartile = data[half_size/2];
                up_quartile = data[half_size + ((int)half_size/2)];
            }
        }
        if ((data.length >= 4) & (data.length %2 != 0)){
            median = (data.length % 2 != 0) ? data[half_size] : ( data[half_size] + data[half_size - 1] )/2;
            if(half_size % 2 == 0){
                low_quartile = (data[(half_size / 2) - 1] + data[half_size / 2] ) / 2;
                up_quartile = (data[half_size+ (half_size/2)] + data[half_size + (half_size/2) + 1] ) / 2;
            }
            else {
                low_quartile = data[half_size/2];
                up_quartile = data[half_size + (half_size/2) +1];
            }
        }
        Median_text.setText(fm.format(median));
        LowQua_text.setText(fm.format(low_quartile));
        UpQua_text.setText(fm.format(up_quartile));


        //Create a histogram
        BarChart histogram = findViewById(R.id.histogram_graph_count);
        //put count of trials as histogram barentries
        ArrayList<BarEntry> entries = new ArrayList<>();
        for(int i = 0; i<trialDataList.size(); i++) {
            entries.add(new BarEntry(i+1, trialDataList.get(i).getCount()));
        }

        //set histogram styles and data
        BarDataSet barDataSet = new BarDataSet(entries, "Trials");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        histogram.setFitBars(true);
        histogram.setData(barData);
        histogram.getDescription().setText("Histogram");
        histogram.animateY(2000);


        //Create a plot
        GraphView graphView = findViewById(R.id.plot_count);
        //Get count of trials for Plot data point
        DataPoint[] dp_for_intcount = new DataPoint[trialDataList.size()];
        for(int i = 0; i<trialDataList.size(); i++) {
            DataPoint ic = new DataPoint(i+1, trialDataList.get(i).getCount());
            dp_for_intcount[i] = ic;

        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dp_for_intcount);

        //title
        graphView.setTitle("Plot of Trials");
        //color
        graphView.setTitleColor(R.color.black);
        //title size
        graphView.setTitleTextSize(26);
        //add data
        graphView.addSeries(series);


        //set back to previous page
        findViewById(R.id.back_button_count).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}