package com.newland.airconditionercontroller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.newland.airconditionercontroller.AirConditionerControllerApplication;
import com.newland.airconditionercontroller.Constant;
import com.newland.airconditionercontroller.R;
import com.newland.airconditionercontroller.activity.marker.DetailsMarkerView;
import com.newland.airconditionercontroller.activity.marker.DetailsMarkerView2;
import com.newland.airconditionercontroller.activity.marker.PositionMarker;
import com.newland.airconditionercontroller.activity.marker.RoundMarker;
import com.newland.airconditionercontroller.activity.marker.Serializables;
import com.newland.airconditionercontroller.base.BaseActivity;
import com.newland.airconditionercontroller.bean.DeviceInfo;
import com.newland.airconditionercontroller.utils.DataCache;
import com.newland.airconditionercontroller.utils.LogUtil;
import com.newland.airconditionercontroller.utils.SPHelper;
import com.newland.airconditionercontroller.utils.mmNetWorkBusiness;
//import com.newland.airconditionercontroller.utils.mmNetWorkBusiness;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import cn.com.newland.nle_sdk.responseEntity.Device;
import cn.com.newland.nle_sdk.responseEntity.DeviceState;
import cn.com.newland.nle_sdk.responseEntity.User;
import cn.com.newland.nle_sdk.responseEntity.base.BaseResponseEntity;
import cn.com.newland.nle_sdk.util.NetWorkBusiness;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.BarLineChartTouchListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main2Activity extends BaseActivity {



    MyLineChart mLineChart;

    List<Entry> entriess=new ArrayList<>();
    int []data=new int[60];
    public TextView mUperLimitText, mUperLimitTitle, mLowerLimitText, mLowerLimitTitle, mCurrentTempText, mCurrentTempTextTitle, mOnlineText;
    public ImageView mAirStateImageView;
    public LinearLayout mOnlineLayout, mCurrentTempLayout;
    public Button zxjz,lssj;

    public int pdrsval,ddsjval;
    public int mAlarmMaxValue = 30;
    public static final int GET_REMOTE_INFO = 101;
    public static final int flag1 = 111;
    public static final int flag2 = 1100;
    public static final int flag3 = 1110;
    public static final int GET_REMOTE_INFO_DELAY = 1000;
    public String mDeviceId;
    public SPHelper spHelper;
    public NetWorkBusiness mNetWorkBusiness;
    public boolean isDeviceExist = false;
    public boolean isDeviceOnLine = false;
    public boolean isPowerOn = false;
    public boolean isAlarmStatus = false;
    public int mBlueColor, mGrayColor, mAlarmColor;
    public Context mContext;
    public mmNetWorkBusiness mmNetWorkBusiness=new mmNetWorkBusiness();
    public int flag=0;
    public int flagdisplay=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mLineChart = findViewById(R.id.chart);
        mContext = this;
        spHelper = SPHelper.getInstant(getApplicationContext());
        mDeviceId = spHelper.getStringFromSP(getApplicationContext(), Constant.DEVICE_ID);
        mNetWorkBusiness = new NetWorkBusiness(DataCache.getAccessToken(getApplicationContext()), DataCache.getBaseUrl(getApplicationContext()));
        mAlarmMaxValue = Integer.valueOf(spHelper.getStringFromSPDef(AirConditionerControllerApplication.getInstance(), Constant.ALARM_MAX_VALUE, Constant.ALARM_MAX_VALUE_DEFAULT_VALUE));
        initHeadView();
        setHeadVisable(true);
        initTitleView(this.getString(R.string.app_title)+"历史数据");
        setRithtTitleViewVisable(false);
        //设置界面开启
        setRithtSettingVisable(false);
            //mmNetWorkBusiness.getDatas(mDeviceId,"door_status",this);


        //1.设置x轴和y轴的点
        Log.d("mm2",entriess.toString());
        Intent intent = this.getIntent();
        Serializables serializables=(Serializables)intent.getSerializableExtra("serializables") ;
        data=serializables.getData();
        LogUtil.d("mm7",data.toString());
        for(int i=0;i<60;i++){
            if(data[i]!=0)
                entriess.add(new Entry(i,data[i]));
            }


        //D/mm2: [Entry, x: 0.0 y: 168.0, Entry, x: 5.0 y: 35.0, Entry, x: 10.0 y: 77.0, Entry, x: 15.0 y: 83.0, Entry, x: 20.0 y: 267.0]
        //mmNetWorkBusiness.getDatas(mDeviceId,"temp_value","1","MAX","2019-10-10 16:49:00","",this);
        //entries=MainActivity.entries;
        //for(int i=0;i<60;i++){
        //    entriess.add(new Entry(i,i));
        //}
        //SerInfo serInfo = (SerInfo) getIntent().getSerializableExtra("serinfo");


        //2.把数据赋值到你的线条
        LineDataSet dataSet = new LineDataSet(entriess, "Label"); // add entries to dataset
        dataSet.setDrawCircles(false);
        dataSet.setColor(Color.parseColor("#7d7d7d"));//线条颜色
        dataSet.setCircleColor(Color.parseColor("#7d7d7d"));//圆点颜色
        dataSet.setLineWidth(1f);//线条宽度
        mLineChart.setScaleEnabled(false);


        //mLineChart.getLineData().getDataSets().get(0).setVisible(true);
        //设置样式
        YAxis rightAxis = mLineChart.getAxisRight();

        //设置图表右边的y轴禁用
        rightAxis.setEnabled(false);
        YAxis leftAxis = mLineChart.getAxisLeft();

        //设置图表左边的y轴禁用
        leftAxis.setEnabled(true);
        rightAxis.setAxisMaximum(dataSet.getYMax() * 2);
        leftAxis.setAxisMaximum(dataSet.getYMax() * 2);

        //设置x轴
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setTextColor(Color.parseColor("#333333"));
        xAxis.setTextSize(11f);
        xAxis.setAxisMinimum(0f);
        xAxis.setDrawAxisLine(true);//是否绘制轴线
        xAxis.setDrawGridLines(true);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        xAxis.setGranularity(1f);//禁止放大x轴标签重绘

        List<String> list = new ArrayList<>();


        Calendar calendar = Calendar.getInstance();
    //获取系统的日期
    //年
        int year = calendar.get(Calendar.YEAR);
//月
        int month = calendar.get(Calendar.MONTH)+1;
//日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
//获取系统时间
//小时
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//分钟
        int minute = calendar.get(Calendar.MINUTE);
//秒
        int second = calendar.get(Calendar.SECOND);

        //x轴赋值
        for (int i = 0; i < 60; i++) {
            list.add(month+"-"+day+" "+(hour-1+(minute+60-i)/60)+":"+((minute+60-i)%60));
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(list));



        //透明化图例
        Legend legend = mLineChart.getLegend();
        legend.setForm(Legend.LegendForm.NONE);
        legend.setTextColor(Color.WHITE);
        //legend.setYOffset(-2);


        //点击图表坐标监听

        mLineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //查看覆盖物是否被回收
                if (mLineChart.isMarkerAllNull()) {
                    //重新绑定覆盖物
                    createMakerView();
                    //并且手动高亮覆盖物
                    mLineChart.highlightValue(h);
                }
            }


            @Override
            public void onNothingSelected() {

            }
        });


        //隐藏x轴描述
        Description description = new Description();
        description.setEnabled(false);
        mLineChart.setDescription(description);


        //创建覆盖物
        createMakerView();


        //3.chart设置数据
        LineData lineData = new LineData(dataSet);


        //是否绘制线条上的文字
        lineData.setDrawValues(false);
        mLineChart.setData(lineData);
        mLineChart.invalidate(); // refresh


    }

    /**
     * 创建覆盖物
     */
    public void createMakerView() {
        DetailsMarkerView detailsMarkerView = new DetailsMarkerView(this);
        detailsMarkerView.setChartView(mLineChart);
        mLineChart.setDetailsMarkerView(detailsMarkerView);
        mLineChart.setPositionMarker(new PositionMarker(this));
        mLineChart.setRoundMarker(new RoundMarker(this));
    }

    @Override
    protected void setRightSetting() {
        super.setRightSetting();
    }
}
