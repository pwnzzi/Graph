package com.example.graph;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity  {

    long mNow;
    Date mDate;
    SimpleDateFormat mformat= new SimpleDateFormat("dd");
    List<Entry> entries = new ArrayList<>();
    SQLiteDatabase db;
    DBHelper dbHelper;
    Cursor cursor;


    final static String dbName = "chart.db";
    final static int dbVersion = 2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dbHelper= new DBHelper(this,dbName,null,dbVersion);

        Chart();

        Thread th=new Thread(new Runnable() {
            @Override
            public void run() {
              //  mNow=System.currentTimeMillis();
               // mDate=new Date(mNow);
                //mformat.format(mDate);
                //int Ndate= Integer.parseInt(mformat.format(mDate)); //현재 요일
                //int Cdate; // db에 저장되어있는 요일


               while(true){

                  Log.i("hello","thred");
                   try{
                       Thread.sleep(100);
                   }catch (InterruptedException e){
                       e.printStackTrace();
                   }

               }
            }
        });
        th.start();



    }











    public void Chart(){

        LineChart lineChart;


       lineChart = (LineChart)findViewById(R.id.chart);


        db=dbHelper.getWritableDatabase();
        cursor= db.query("CHART",null,null,null,null,null,null);
        if(cursor.getCount()>0){
            Log.i("database not empty","database not empty");

        }else{
            //db가 비어있으면 데이터를 넣어준다. 현재 요일을 정수로 바꾸어 db로 보내준다.
            Log.i("database empty","database empty");
            mNow=System.currentTimeMillis();
            mDate=new Date(mNow);
            mformat.format(mDate);
            int time= Integer.parseInt(mformat.format(mDate));
            // 현재 요일을 기준으로 총 7개의 데이터를 넣어준다.
            for(int i=0 ; i<7; i++){
                dbHelper.insert(time+ i ,0);
            }

        }


        int xvalue,yvalue;

        for(int i=0; i<7;i++){
            //db에 저장되어있는 x,y값을 순서대로 가져와 그래프에 표시해준다.
            cursor.moveToPosition(i);
            xvalue=cursor.getInt(cursor.getColumnIndex("date"));
            yvalue=cursor.getInt(cursor.getColumnIndex("cal"));

            entries.add(new Entry(xvalue, yvalue));
        }
        int k= 14,j=6;
        dbHelper.update(k,j);
       // dbHelper.delete(12);
        //dbHelper.delete(13);
        //dbHelper.delete(14);


        LineDataSet lineDataSet = new LineDataSet(entries, "운동량");


        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleRadius(6);
        lineDataSet.setCircleColor(Color.parseColor("#3ea055"));
        lineDataSet.setCircleColorHole(Color.WHITE);
        lineDataSet.setColor(Color.parseColor("#3ea055"));
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.setDrawValues(false);
        lineDataSet.setDrawFilled(true);
        if(Utils.getSDKInt()>18){
            Drawable drawable= ContextCompat.getDrawable(this,R.drawable.fade_green);
            lineDataSet.setFillDrawable(drawable);
        }else{
            lineDataSet.setFillColor(Color.BLACK);
        }

        LineData lineData = new LineData(lineDataSet);


        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setDrawGridLines(false);
        xAxis.enableGridDashedLine(8, 24, 0);

        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);

        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);
        yRAxis.setDrawGridLines(false);
        yLAxis.setDrawGridLines(false);
        yLAxis.setLabelCount(3,true);


        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription("");
        lineChart.animateY(2000, Easing.EasingOption.EaseInCubic);
        lineChart.invalidate();

    }




}
